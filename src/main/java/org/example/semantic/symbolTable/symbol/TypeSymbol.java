package org.example.semantic.symbolTable.symbol;

import java.util.Objects;

public class TypeSymbol extends AbstractSymbol {

    public TypeSymbol(String name) {
        super(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TypeSymbol other) {
            return other.mName.equals(mName);
        }

        return false;
    }
}
