package org.example.semantic.symbolTable;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

public class SymbolTable {
    private Scope mActualScope;

    public void addNewScope(AbstractDescriptor scopeDescriptor) {
        Scope newScope = new Scope(mActualScope, scopeDescriptor);
        Scope parentScope = mActualScope;

        if (parentScope != null) {
            parentScope.mChildrenScopes.add(parentScope);
        }

        mActualScope = newScope;
    }

    public void addSymbolToActualScope(Symbol symbol, AbstractDescriptor scopeDescriptor) {
        mActualScope.addSymbol(symbol, scopeDescriptor);
    }
}
