package org.example.primitive.expression;

public class BinaryExpression extends AbstractExpression {
    public final AbstractExpression mLeftExpression;
    public final AbstractExpression mRightExpression;

    public BinaryExpression(AbstractExpression leftExpression,
                             AbstractExpression rightExpression, ExpressionType operator) {
        super(operator);
        mLeftExpression = leftExpression;
        mRightExpression = rightExpression;
    }

    @Override
    public String toString() {
        return mLeftExpression.toString() + mExpressionType.toString() + mRightExpression.toString();
    }
}
