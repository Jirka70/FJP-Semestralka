package org.example.ast.expression;

import org.example.util.Location;

public abstract class UnaryExpression extends AbstractExpression {
    public final AbstractExpression mExpression;

    public UnaryExpression(AbstractExpression expression, ExpressionType operator, Location location) {
        super(operator, location);
        mExpression = expression;
    }
}
