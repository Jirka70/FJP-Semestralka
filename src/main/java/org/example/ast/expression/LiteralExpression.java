package org.example.ast.expression;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.*;
import org.example.util.Location;

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

    public LiteralExpression(String literal, LiteralType type, Location location) {
        super(ExpressionType.LITERAL, location);
        mLiteral = literal;
        mType = type;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        if (isIntegerLiteral()) {
            return new IntType();
        }

        if (isFloatLiteral()) {
            return new FloatType();
        }

        if (isBooleanLiteral()) {
            return new BooleanType();
        }

        if (isObjectLiteral()) {
            if (mType == LiteralType.STRING_LITERAL) {
                return new ObjectType("String");
            }

            return new ObjectType("null");
        }

        throw new TypeMismatchException("Type of literal " + mLiteral + " is not defined");
    }

    private boolean isObjectLiteral() {
        return mType == LiteralType.STRING_LITERAL
                || mType == LiteralType.NULL_LITERAL;
    }

    private boolean isBooleanLiteral() {
        return mType == LiteralType.BOOL_LITERAL;
    }

    private boolean isFloatLiteral() {
        return isIntegerLiteral()
                || mType == LiteralType.FLOAT_LITERAL
                || mType == LiteralType.HEX_FLOAT_LITERAL;
    }

    private boolean isIntegerLiteral() {
        return mType == LiteralType.CHAR_LITERAL
                || mType == LiteralType.DECIMAL_LITERAL
                || mType == LiteralType.HEX_LITERAL
                || mType == LiteralType.OCT_LITERAL
                || mType == LiteralType.BINARY_LITERAL;
    }

    @Override
    public String toString() {
        return mLiteral;
    }

    @Override
    public void analyze(AbstractScope abstractScope) {

    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }
}
