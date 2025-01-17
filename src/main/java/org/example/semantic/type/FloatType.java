package org.example.semantic.type;


import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;

public class FloatType extends NumberType {
    private static final String FLOAT_KEYWORD = "float";

    public FloatType() {
        super(FLOAT_KEYWORD);
    }

    @Override
    public boolean isCompatibleWith(AbstractType other) {
        return other instanceof FloatType
                || other instanceof IntType
                || other instanceof CharType;
    }

    @Override
    public boolean canBeAssignedTo(AbstractType identifierType) {
        return identifierType instanceof FloatType;
    }

    @Override
    public AbstractType combineWith(AbstractType other) throws SemanticException {
        if (!isCompatibleWith(other)) {
            throw new TypeMismatchException("Type \"" + mName + "\" is not compatible with \"" + other.mName);
        }

        return new FloatType();
    }
}
