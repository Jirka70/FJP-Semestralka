package org.example.ast.expression;

import org.example.util.Location;

public abstract class PrimaryExpression extends AbstractExpression {

    public PrimaryExpression(ExpressionType operator, Location location) {
        super(operator, location);
    }
}
