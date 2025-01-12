package org.example.ast.expression;

public class PrefixExpression extends UnaryExpression {
    public PrefixExpression(AbstractExpression expression, ExpressionType operand) {
        super(expression, operand);
    }

    @Override
    public String toString() {
        return mExpressionType.toString() + mExpression.toString();
    }
}