package org.example.ast.expression;

public abstract class AbstractExpression {
    public final ExpressionType mExpressionType;

    protected AbstractExpression(ExpressionType expressionType) {
        mExpressionType = expressionType;
    }

    @Override
    public abstract String toString();
}
