package org.example.semantic.symbolTable.symbol;

public abstract class AbstractSymbol {
    public final String mName;

    protected AbstractSymbol(String name) {
        mName = name;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}
