package org.example.semantic.symbolTable.scope;

import org.example.semantic.symbolTable.Symbol;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;

import java.util.Map;
import java.util.HashMap;

public class ScopeTable {
    public Map<Symbol, AbstractDescriptor> mScopeTable = new HashMap<>();

    public void addSymbol(Symbol symbol, AbstractDescriptor descriptor) {
        mScopeTable.put(symbol, descriptor);
    }

    public boolean containsSymbol(Symbol symbol) {
        return mScopeTable.containsKey(symbol);
    }

    public AbstractDescriptor getDescriptor(Symbol symbol) {
        return mScopeTable.get(symbol);
    }
}
