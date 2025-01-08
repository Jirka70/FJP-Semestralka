package org.example.primitive.expression;

public class CastExpression extends UnaryExpression {
    public final String mtypeType;

    public CastExpression(AbstractExpression expression, String typeType) {
        super(expression, ExpressionType.CAST);
        this.mtypeType = typeType;
    }

    @Override
    public String toString() {
        return "(" + mtypeType + ")" + mExpression.toString();
    }
}
