package org.example.primitive.expression;

public class PostfixExpression extends UnaryExpression {
    public PostfixExpression(AbstractExpression expression, ExpressionType operand) {
        super(expression, operand);
    }

    @Override
    public String toString() {
        return mExpression.toString() + mExpressionType.toString();
    }
}