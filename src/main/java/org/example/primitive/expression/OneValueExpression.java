package org.example.primitive.expression;

/**
 * Stores only one value (e. g. 3.14)
 * */
public class OneValueExpression extends AbstractExpression {

    public final Object mValue;

    public OneValueExpression(Object value) {
        super(ExpressionType.VALUE);
        mValue = value;
    }
}
