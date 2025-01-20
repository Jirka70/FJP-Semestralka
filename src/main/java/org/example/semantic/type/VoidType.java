package org.example.semantic.type;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.NullCombineException;

public class VoidType extends AbstractType {
    private static final String VOID_TYPE = "vacuum";

    public VoidType() {
        super(VOID_TYPE);
    }

    @Override
    public boolean isCompatibleWith(AbstractType other) {
        return false;
    }

    @Override
    public boolean canBeAssignedTo(AbstractType identifierType) {
        return identifierType instanceof VoidType;
    }

    @Override
    public AbstractType combineWith(AbstractType other) throws SemanticException {
        throw new NullCombineException(VOID_TYPE + " cannot be combined with any type");
    }
}
