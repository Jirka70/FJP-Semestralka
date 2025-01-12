package org.example.ast.expression;

public class ParExpression extends PrimaryExpression {
    public final AbstractExpression mExpression;

    public ParExpression(AbstractExpression expression) {
        super(ExpressionType.PARENTHESES);
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "(" + mExpression.toString() + ")";
    }
}
