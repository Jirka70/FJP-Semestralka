package org.example.semantic.type;

import org.example.semantic.exception.symbolTableException.NullCombineException;

public class NullType extends ObjectType {
    private static final String NULL_KEYWORD = "null";

    public NullType() {
        super(NULL_KEYWORD);
    }

    @Override
    public boolean isCompatibleWith(AbstractType other) {
        return false;
    }

    @Override
    public boolean canBeAssignedTo(AbstractType identifierType) {
        return identifierType instanceof ObjectType;
    }

    @Override
    public AbstractType combineWith(AbstractType other) throws NullCombineException {
        throw new NullCombineException("Null cannot be combined with any type");
    }
}
