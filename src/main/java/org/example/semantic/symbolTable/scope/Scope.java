package org.example.semantic.symbolTable.scope;

import org.example.semantic.symbolTable.Symbol;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;

import java.util.ArrayList;
import java.util.List;

public class Scope {
    public final AbstractDescriptor mScopeDescriptor;
    public final Scope mParentScope;
    public final List<Scope> mChildrenScopes = new ArrayList<>();
    private final ScopeTable mScopeTable = new ScopeTable();

    public Scope(Scope parentScope, AbstractDescriptor scopeDescriptor) {
        mParentScope = parentScope;
        mScopeDescriptor = scopeDescriptor;
    }

    public void addChildScope(Scope scope) {
        mChildrenScopes.add(scope);
    }

    public void addSymbol(Symbol symbol, AbstractDescriptor descriptor) {
        mScopeTable.addSymbol(symbol, descriptor);
    }

    public boolean containsSymbol(Symbol symbol) {
        return mScopeTable.containsSymbol(symbol);
    }

    public AbstractDescriptor getSymbolDescriptor(Symbol symbol) {
        return mScopeTable.getDescriptor(symbol);
    }

    public boolean hasParent() {
        return mParentScope != null;
    }
}
