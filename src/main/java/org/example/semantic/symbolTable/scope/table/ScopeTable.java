package org.example.semantic.symbolTable.scope.table;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScopeTable {
    public final Map<AbstractSymbol, AbstractDescriptor> mScopeTable = new HashMap<>();

    public void addSymbol(AbstractSymbol symbol, AbstractDescriptor descriptor) {
        mScopeTable.put(symbol, descriptor);
    }

    public boolean containsSymbol(AbstractSymbol symbol) {
        return mScopeTable.containsKey(symbol);
    }

    public AbstractDescriptor getDescriptor(AbstractSymbol symbol) {
        return mScopeTable.get(symbol);
    }
    public Set<AbstractSymbol> getAllSymbols() {
        return mScopeTable.keySet();
    }

    public Collection<AbstractDescriptor> getAllDescriptors() {
        return mScopeTable.values();
    }
}
