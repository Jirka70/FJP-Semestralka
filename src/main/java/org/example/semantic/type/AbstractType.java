package org.example.semantic.type;


import org.example.semantic.exception.SemanticException;

import java.util.Objects;

public abstract class AbstractType {
    public final String mName;

    protected AbstractType(String name) {
        mName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractType that)) return false;
        return Objects.equals(mName, that.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mName);
    }

    /**
     * Returns if those two types could be used together in one expression (e.g. 2 + 3.14)
     * */
    public abstract boolean isCompatibleWith(AbstractType other);
    public abstract boolean canBeAssignedTo(AbstractType identifierType);
    public abstract AbstractType combineWith(AbstractType other) throws SemanticException;
    public String toString() {
        return getClass().getSimpleName();
    }
}

