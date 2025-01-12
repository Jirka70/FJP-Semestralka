package org.example.ast.expression;

public class IdentifierExpression extends PrimaryExpression {
    public final String mIdentifier;

    public IdentifierExpression(String identifier) {
        super(ExpressionType.IDENTIFIER);
        mIdentifier = identifier;
    }

    @Override
    public String toString() {
        return mIdentifier;
    }
}
