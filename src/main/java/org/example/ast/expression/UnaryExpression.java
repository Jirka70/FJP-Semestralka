package org.example.ast.expression;

public abstract class UnaryExpression extends AbstractExpression {
    public final AbstractExpression mExpression;

    public UnaryExpression(AbstractExpression expression, ExpressionType operator) {
        super(operator);
        mExpression = expression;
    }
}
