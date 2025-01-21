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
        } else if (isObjectCreation(ctx)) {
            return extractObjectCreation(ctx);
        } else if (isArrayIndex(ctx)) {
            return extractArrayIndex(ctx);
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
            return new LiteralVisitor().visit(primCtx.literal());
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

    private AbstractExpression extractArrayIndex(IavaParser.ExpressionContext ctx) {
        return new ArrayExpression(visit(ctx.expression(0)), visit(ctx.expression(1)), getExpresssionLocation(ctx));
    }

    private boolean isArrayIndex(IavaParser.ExpressionContext ctx) {
        return ctx.LBRACK() != null && ctx.RBRACK() != null;
    }

    private AbstractExpression extractObjectCreation(IavaParser.ExpressionContext ctx) {
        String createdName = ctx.creator().createdName().getText();
        IavaParser.ArrayCreatorRestContext restContext = ctx.creator().arrayCreatorRest();
        if (!restContext.expression().isEmpty()) {
            AbstractExpression sizeExpression = visit(restContext.expression(0));
            return new ArrayCreationExpression(createdName, sizeExpression, getExpresssionLocation(ctx));
        } else {
            IavaParser.ArrayInitializerContext initCtx = restContext.arrayInitializer();
            List<AbstractExpression> initializer = new ArrayList<>();
            for (IavaParser.VariableInitializerContext varInitCtx : initCtx.variableInitializer())
                initializer.add(visit(varInitCtx.expression()));
            return new ArrayCreationExpression(createdName, initializer, getExpresssionLocation(ctx));
        }
    }

    private boolean isObjectCreation(IavaParser.ExpressionContext ctx) {
        return ctx.creator() != null;
    }
}
