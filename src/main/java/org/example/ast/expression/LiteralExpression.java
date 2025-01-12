package org.example.ast.expression;

public class LiteralExpression extends PrimaryExpression {
    public enum LiteralType {
        CHAR_LITERAL,
        STRING_LITERAL,
        BOOL_LITERAL,
        NULL_LITERAL,
        DECIMAL_LITERAL,
        HEX_LITERAL,
        OCT_LITERAL,
        BINARY_LITERAL,
        FLOAT_LITERAL,
        HEX_FLOAT_LITERAL
    }

    public final String mLiteral;
    public final LiteralType mType;

    public LiteralExpression(String literal, LiteralType type) {
        super(ExpressionType.LITERAL);
        mLiteral = literal;
        mType = type;
    }

    @Override
    public String toString() {
        return mLiteral;
    }
}
