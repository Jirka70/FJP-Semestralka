package org.example.primitive.expression;

public class BooleanExpression extends AbstractExpression {
    public final AbstractExpression mLeftExpression;
    public final AbstractExpression mRightExpression;

    public BooleanExpression(AbstractExpression leftExpression,
                             AbstractExpression rightExpression) {
        super(ExpressionType.BOOL_EQUALS);
        mLeftExpression = leftExpression;
        mRightExpression = rightExpression;
    }
}
