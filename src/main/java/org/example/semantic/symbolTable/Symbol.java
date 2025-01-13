package org.example.semantic.symbolTable;

public class Symbol {
    public final String mSymbol;

    public Symbol(String symbol) {
        mSymbol = symbol;
    }

    @Override
    public int hashCode() {
        return mSymbol.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Symbol other)) return false;
        return other.mSymbol.equals(mSymbol);
    }

    @Override
    public String toString() {
        return "Symbol: " + mSymbol;
    }
}
