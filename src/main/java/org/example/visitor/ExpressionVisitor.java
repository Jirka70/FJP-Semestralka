package org.example.visitor;

import org.antlr.v4.runtime.Token;
import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.expression.*;

import java.util.ArrayList;
import java.util.List;


public class ExpressionVisitor extends IavaParserBaseVisitor<AbstractExpression> {

    @Override
    public AbstractExpression visitExpression(IavaParser.ExpressionContext ctx) {
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

        BinaryExpression expr = new BinaryExpression(leftExpression, rightExpression, ExpressionType.valueOfByOperator(operatorStr));
        //System.out.println("Binary: " + expr);
        return new BinaryExpression(leftExpression, rightExpression, ExpressionType.valueOfByOperator(operatorStr));
    }

    private PrimaryExpression extractPrimaryExpression(IavaParser.ExpressionContext ctx) {
        IavaParser.PrimaryContext primCtx = ctx.primary();
        if (primCtx.expression() != null) {
            AbstractExpression expr = visit(primCtx.expression());
            //System.out.println("ParExpr: " + new ParExpression(expr));
            return new ParExpression(expr);
        } else if (primCtx.identifier() != null) {
            //System.out.println("IdentifierExpr: " + new IdentifierExpression(primCtx.identifier().getText()));
            return new IdentifierExpression(primCtx.identifier().getText());
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
            //System.out.println("LitExpr: " + new LiteralExpression(litCtx.getText(), type));
            return new LiteralExpression(litCtx.getText(), type);
        }

        throw new IllegalArgumentException("Type of primary expression " + ctx.getText() + " not recognized");
    }

    private boolean isPrimaryExpression(IavaParser.ExpressionContext ctx) {
        return ctx.primary() != null;
    }

//    private boolean isBooleanExpression(IavaParser.ExpressionContext ctx) {
//        return ctx.EQUAL() != null
//                || ctx.AND() != null
//                || ctx.OR() != null
//                || ctx.NOTEQUAL() != null
//                || ctx.GE() != null
//                || ctx.LE() != null
//                || ctx.LT().size() == 1
//                || ctx.GT().size() == 1;
//    }

    private boolean isBinaryExpression(IavaParser.ExpressionContext ctx) {
        return ctx.expression().size() == 2 && ctx.bop != null;
    }

    private boolean isUnaryExpression(IavaParser.ExpressionContext ctx) {
        return ctx.expression().size() == 1 && ctx.bop == null;
    }

    private UnaryExpression extractUnaryExpression(IavaParser.ExpressionContext ctx) {
        AbstractExpression innerExpr = visit(ctx.expression(0));

        if (ctx.typeType() != null) {
            CastExpression e = new CastExpression(innerExpr, ctx.typeType().getText());
            System.out.println("Cast: " + e);
            return new CastExpression(innerExpr, ctx.typeType().getText());
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
            PrefixExpression e = new PrefixExpression(innerExpr, type);
            System.out.println("Prefix: " + e);
            return new PrefixExpression(innerExpr, type);
        } else if (ctx.postfix != null) {
            ExpressionType type = switch (ctx.postfix.getText()) {
                case "++" -> ExpressionType.POST_INC;
                case "--" -> ExpressionType.POST_DEC;
                default -> null;
            };
            PostfixExpression e = new PostfixExpression(innerExpr, type);
            //System.out.println("Postfix: " + e);
            return new PostfixExpression(innerExpr, type);
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
        return new MethodCallExpression(name, args);
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

        TernaryExpression expr = new TernaryExpression(conditionExpression, trueBranchExpression, falseBranchExpression);
        //System.out.println("Ternary: " + expr);
        return new TernaryExpression(conditionExpression, trueBranchExpression, falseBranchExpression);
    }
}
