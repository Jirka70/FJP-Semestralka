package org.example.visitor;

import org.antlr.v4.runtime.Token;
import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.expression.*;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;


public class ExpressionVisitor extends IavaParserBaseVisitor<AbstractExpression> {

    @Override
    public AbstractExpression visitExpression(IavaParser.ExpressionContext ctx) {
        System.out.println("expression: " + ctx.getText());
        if (isTernaryExpression(ctx)) {
            return extractTernaryExpression(ctx);
        } else if (isPrimaryExpression(ctx)) {
            return extractPrimaryExpression(ctx);
        } else if (isBinaryExpression(ctx)) {
            return extractBinaryExpression(ctx);
        } else if (isUnaryExpression(ctx)) {
            return extractUnaryExpression(ctx);
        } else if (isMethodCallExpression(ctx)) {
            return extractMethodCallExpression(ctx);
        }

        throw new IllegalArgumentException("Type of expression " + ctx.getText() + " not recognized");
    }

    @Override
    public AbstractExpression visitParExpression(IavaParser.ParExpressionContext ctx) {
        return visit(ctx.expression());
    }

    private BinaryExpression extractBinaryExpression(IavaParser.ExpressionContext ctx) {
        int expectedExpressionSize = 2;
        if (ctx.expression().size() != expectedExpressionSize) {
            throw new IllegalArgumentException("Expression " + ctx.getText() + " is not a binary expression");
        }

        IavaParser.ExpressionContext left = ctx.expression(0);
        IavaParser.ExpressionContext right = ctx.expression(1);
        Token operator = ctx.bop;
        if (operator == null) {
            throw new IllegalArgumentException("Expression " + ctx.getText() + " is not a binary expression");
        }

        AbstractExpression leftExpression = visit(left);
        AbstractExpression rightExpression = visit(right);
        String operatorStr = operator.getText();

        Location location = getExpresssionLocation(ctx);
        return new BinaryExpression(leftExpression, rightExpression, ExpressionType.valueOfByOperator(operatorStr),
                location);
    }

    private Location getExpresssionLocation(IavaParser.ExpressionContext ctx) {
        return new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    private PrimaryExpression extractPrimaryExpression(IavaParser.ExpressionContext ctx) {
        Location location = getExpresssionLocation(ctx);
        IavaParser.PrimaryContext primCtx = ctx.primary();
        if (primCtx.expression() != null) {
            AbstractExpression expr = visit(primCtx.expression());
            return new ParExpression(expr, location);
        } else if (primCtx.identifier() != null) {
            return new IdentifierExpression(primCtx.identifier().getText(), location);
        } else if (primCtx.literal() != null) {
            IavaParser.LiteralContext litCtx = primCtx.literal();
            LiteralExpression.LiteralType type = null;
            if (litCtx.integerLiteral() != null) {
                IavaParser.IntegerLiteralContext intLitCtx = litCtx.integerLiteral();
                if (intLitCtx.DECIMAL_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.DECIMAL_LITERAL;
                } else if (intLitCtx.HEX_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.HEX_LITERAL;
                } else if (intLitCtx.OCT_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.OCT_LITERAL;
                } else if (intLitCtx.BINARY_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.BINARY_LITERAL;
                }
            } else if (litCtx.floatLiteral() != null) {
                IavaParser.FloatLiteralContext floatLitCtx = litCtx.floatLiteral();
                if (floatLitCtx.FLOAT_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.FLOAT_LITERAL;
                } else if (floatLitCtx.HEX_FLOAT_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.HEX_FLOAT_LITERAL;
                }
            } else {
                if (litCtx.CHAR_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.CHAR_LITERAL;
                } else if (litCtx.STRING_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.STRING_LITERAL;
                } else if (litCtx.BOOL_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.BOOL_LITERAL;
                } else if (litCtx.NULL_LITERAL() != null) {
                    type = LiteralExpression.LiteralType.NULL_LITERAL;
                }
            }
            return new LiteralExpression(litCtx.getText(), type, location);
        }

        throw new IllegalArgumentException("Type of primary expression " + ctx.getText() + " not recognized");
    }

    private boolean isPrimaryExpression(IavaParser.ExpressionContext ctx) {
        return ctx.primary() != null;
    }


    private boolean isBinaryExpression(IavaParser.ExpressionContext ctx) {
        return ctx.expression().size() == 2 && ctx.bop != null;
    }

    private boolean isUnaryExpression(IavaParser.ExpressionContext ctx) {
        return ctx.expression().size() == 1 && ctx.bop == null;
    }

    private UnaryExpression extractUnaryExpression(IavaParser.ExpressionContext ctx) {
        AbstractExpression innerExpr = visit(ctx.expression(0));

        if (ctx.typeType() != null) {
            Location location = getExpresssionLocation(ctx);
            return new CastExpression(innerExpr, ctx.typeType().getText(), location);
        } else if (ctx.prefix != null) {
            ExpressionType type = switch (ctx.prefix.getText()) {
                case "++" -> ExpressionType.PRE_INC;
                case "--" -> ExpressionType.PRE_DEC;
                case "~" ->  ExpressionType.TILDE;
                case "!" ->  ExpressionType.NEG;
                case "+" ->  ExpressionType.UNARY_PLUS;
                case "-" ->  ExpressionType.UNARY_MINUS;
                default -> null;
            };
            PrefixExpression e = new PrefixExpression(innerExpr, type, getExpresssionLocation(ctx));
            System.out.println("Prefix: " + e);
            return new PrefixExpression(innerExpr, type, getExpresssionLocation(ctx));
        } else if (ctx.postfix != null) {
            ExpressionType type = switch (ctx.postfix.getText()) {
                case "++" -> ExpressionType.POST_INC;
                case "--" -> ExpressionType.POST_DEC;
                default -> null;
            };
            return new PostfixExpression(innerExpr, type, getExpresssionLocation(ctx));
        }

        throw new IllegalArgumentException("Type of unary expression " + ctx.getText() + " not recognized");
    }

    private boolean isMethodCallExpression(IavaParser.ExpressionContext ctx) {
        return ctx.methodCall() != null;
    }

    private MethodCallExpression extractMethodCallExpression(IavaParser.ExpressionContext ctx) {
        String name = ctx.methodCall().identifier().getText();
        IavaParser.ArgumentsContext methodArguments = ctx.methodCall().arguments();
        IavaParser.ExpressionListContext argumentsExpression = methodArguments.expressionList();

        List<IavaParser.ExpressionContext> argsCtx = argumentsExpression == null // method has no arguments
                ? new ArrayList<>()
                : argumentsExpression.expression();

        List<AbstractExpression> args = new ArrayList<>();
        for (IavaParser.ExpressionContext argCtx : argsCtx)
            args.add(visit(argCtx));

        //System.out.println("Method call: " + new MethodCallExpression(name, args));
        return new MethodCallExpression(name, args, getExpresssionLocation(ctx));
    }

    private boolean isTernaryExpression(IavaParser.ExpressionContext ctx) {
        return ctx.QUESTION() != null && ctx.COLON() != null;
    }

    private TernaryExpression extractTernaryExpression(IavaParser.ExpressionContext ctx) {
        int expectedExpressionSize = 3;
        if (ctx.expression().size() != expectedExpressionSize) {
            throw new IllegalArgumentException("Expression " + ctx.getText() + " is not a ternary expression");
        }

        IavaParser.ExpressionContext condition = ctx.expression(0);

        if (!isBinaryExpression(condition)) {
            throw new IllegalArgumentException("Ternary Expression does not contain condition");
        }

        IavaParser.ExpressionContext trueBranch = ctx.expression(1);
        IavaParser.ExpressionContext falseBranch = ctx.expression(2);

        AbstractExpression conditionExpression = visit(condition);
        AbstractExpression trueBranchExpression = visit(trueBranch);
        AbstractExpression falseBranchExpression = visit(falseBranch);

        return new TernaryExpression(conditionExpression, trueBranchExpression, falseBranchExpression,
                getExpresssionLocation(ctx));
    }
}
