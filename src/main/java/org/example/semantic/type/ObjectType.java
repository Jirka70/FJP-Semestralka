package org.example.semantic.type;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;

public class ObjectType extends AbstractType {

    public ObjectType(String name) {
        super(name);
    }

    @Override
    public boolean isCompatibleWith(AbstractType other) {
        return equals(other);
    }

    @Override
    public boolean canBeAssignedTo(AbstractType identifierType) {
        return equals(identifierType);
    }

    @Override
    public AbstractType combineWith(AbstractType other) throws SemanticException {
        throw new TypeMismatchException("Type \"" + mName + "\" is not compatible with \"" + other.mName + "\"");
    }
}
