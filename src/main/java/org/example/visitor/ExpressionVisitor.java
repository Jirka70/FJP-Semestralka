package org.example.visitor;

import org.antlr.v4.runtime.Token;
import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.expression.AbstractExpression;
import org.example.primitive.expression.BooleanExpression;
import org.example.primitive.expression.OneValueExpression;
import org.example.primitive.expression.TernaryExpression;


public class ExpressionVisitor extends IavaParserBaseVisitor<AbstractExpression> {

    @Override
    public AbstractExpression visitExpression(IavaParser.ExpressionContext ctx) {
        if (isTernaryExpression(ctx)) {
            return extractTernaryExpression(ctx);
        } else if (isOneValueExpression(ctx)) {
            return extractOneValueExpression(ctx);
        } else if (isBooleanExpression(ctx)) {
            return extractBooleanExpression(ctx);
        }

        return super.visitExpression(ctx);
    }

    private AbstractExpression extractBooleanExpression(IavaParser.ExpressionContext ctx) {
        int expectedExpressionSize = 2;
        if (ctx.expression().size() != expectedExpressionSize) {
            throw new IllegalArgumentException("Expression " + ctx.getText() + " is not a boolean expression");
        }

        IavaParser.ExpressionContext left = ctx.expression(0);
        IavaParser.ExpressionContext right = ctx.expression(1);
        Token operator = ctx.bop;
        if (operator == null) {
            throw new IllegalArgumentException("Expression " + ctx.getText() + " is not a boolean expression");
        }

        AbstractExpression leftExpression = visit(left);
        AbstractExpression rightExpression = visit(right);

        return new BooleanExpression(leftExpression, rightExpression);
    }

    private OneValueExpression extractOneValueExpression(IavaParser.ExpressionContext ctx) {
        String value = ctx.getText();
        return new OneValueExpression(value);
    }

    private boolean isOneValueExpression(IavaParser.ExpressionContext ctx) {
        return ctx.children.size() == 1;
    }

    private boolean isBooleanExpression(IavaParser.ExpressionContext ctx) {
        return ctx.EQUAL() != null
                || ctx.AND() != null
                || ctx.OR() != null
                || ctx.NOTEQUAL() != null
                || ctx.GE() != null
                || ctx.LE() != null
                || ctx.LT() != null
                || ctx.GT() != null;
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

        if (!isBooleanExpression(condition)) {
            throw new IllegalArgumentException("Ternary Expression does not contain condition");
        }

        IavaParser.ExpressionContext trueBranch = ctx.expression(1);
        IavaParser.ExpressionContext falseBranch = ctx.expression(2);

        AbstractExpression conditionExpression = visit(condition);
        AbstractExpression trueBranchExpression = visit(trueBranch);
        AbstractExpression falseBranchExpression = visit(falseBranch);

        return new TernaryExpression(conditionExpression, trueBranchExpression, falseBranchExpression);
    }
}
