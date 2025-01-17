package org.example.semantic.type;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;

public class CharType extends NumberType {
    public static final String CHAR_TYPE = "char";

    public CharType() {
        super(CHAR_TYPE);
    }

    @Override
    public boolean isCompatibleWith(AbstractType other) {
        return other instanceof CharType
                || other instanceof IntType
                || other instanceof FloatType;
    }

    @Override
    public boolean canBeAssignedTo(AbstractType identifierType) {
        return identifierType instanceof CharType
                || identifierType instanceof IntType
                || identifierType instanceof FloatType;
    }

    @Override
    public AbstractType combineWith(AbstractType other) throws SemanticException {
        if (!isCompatibleWith(other)) {
            throw new TypeMismatchException("Type \"" + mName + "\" is not compatible with \"" + other.mName);
        }

        if (other instanceof CharType) {
            return new CharType();
        } else if (other instanceof IntType) {
            return new IntType();
        } else if (other instanceof FloatType) {
            return new FloatType();
        }

        throw new TypeMismatchException("Type \"" + mName + "\" is not compatible with \"" + other.mName);
    }
}
