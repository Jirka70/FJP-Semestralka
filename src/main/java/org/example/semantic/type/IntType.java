package org.example.semantic.type;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;

public class IntType extends NumberType {
    private static final String INT_TYPE = "int";

    public IntType() {
        super(INT_TYPE);
    }

    @Override
    public boolean isCompatibleWith(AbstractType other) {
        return other instanceof IntType
                || other instanceof CharType
                || other instanceof FloatType;
    }

    @Override
    public boolean canBeAssignedTo(AbstractType identifierType) {
        if (!isCompatibleWith(identifierType)) {
            return false;
        }

        return identifierType instanceof IntType
                || identifierType instanceof CharType
                || identifierType instanceof FloatType;
    }

    @Override
    public AbstractType combineWith(AbstractType other) throws SemanticException {
        if (!isCompatibleWith(other)) {
            throw new TypeMismatchException("Type \"" + mName + "\" is not compatible with \"" + other.mName + "\"");
        }

        if (other instanceof FloatType) {
            return new FloatType();
        }

        return new IntType();
    }
}
