package org.example.semantic.symbolTable.symbol;

import org.example.util.Location;

import java.util.Objects;

public class VariableSymbol extends AbstractSymbol {

    public VariableSymbol(String name) {
        super(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), mName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VariableSymbol other) {
            return mName.equals(other.mName);
        }

        return false;
    }
}
