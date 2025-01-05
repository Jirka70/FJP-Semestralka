package org.example.primitive.expression;

public abstract class AbstractExpression {
    public final ExpressionType mExpressionType;

    protected AbstractExpression(ExpressionType expressionType) {
        mExpressionType = expressionType;
    }
}
