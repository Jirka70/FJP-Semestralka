package org.example.semantic.symbolTable.symbol;

import java.util.Objects;

public class ClassSymbol extends AbstractSymbol {

    public ClassSymbol(String name) {
        super(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), mName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassSymbol other) {
            return mName.equals(other.mName);
        }

        return false;
    }
}
