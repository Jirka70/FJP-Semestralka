package org.example.semantic.type;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;

public class BooleanType extends PrimitiveType {
    private static final String BOOLEAN_TYPE = "boolean";

    public BooleanType() {
        super(BOOLEAN_TYPE);
    }

    @Override
    public boolean isCompatibleWith(AbstractType other) {
        return other instanceof BooleanType;
    }

    @Override
    public boolean canBeAssignedTo(AbstractType identifierType) {
        return identifierType instanceof BooleanType;
    }

    @Override
    public AbstractType combineWith(AbstractType other) throws SemanticException {
        throw new TypeMismatchException("Type \"" + mName + "\" is not compatible with \"" + other.mName);
    }
}
