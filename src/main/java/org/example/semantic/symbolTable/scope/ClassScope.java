package org.example.semantic.symbolTable.scope;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.scope.table.ScopeTable;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.util.Location;

public class ClassScope extends AbstractScope {
    public final ScopeTable mScopeTable = new ScopeTable();

    public ClassScope(AbstractScope parentScope, AbstractDescriptor descriptor) {
        super(parentScope, descriptor);
    }

    @Override
    public boolean isSymbolDefinedOnLocation(AbstractSymbol symbol, Location location) {
        if (containsSymbolInScopeOnLocation(symbol, location)) {
            return true;
        }

        if (hasParent()) {
            return mParentAbstractScope.isSymbolDefinedOnLocation(symbol, location);
        }

        return false;
    }

    @Override
    public void addSymbol(AbstractSymbol symbol, AbstractDescriptor descriptor, Location location) {
        mScopeTable.addSymbol(symbol, descriptor);
    }

    @Override
    public boolean containsSymbolInScopeOnLocation(AbstractSymbol symbol, Location location) {
        return mScopeTable.containsSymbol(symbol);
    }

    @Override
    public AbstractDescriptor getSymbolDescriptorOnLocation(AbstractSymbol symbol, Location location) {
        if (mScopeTable.containsSymbol(symbol)) {
            return mScopeTable.getDescriptor(symbol);
        }

        if (hasParent()) {
            return mParentAbstractScope.getSymbolDescriptorOnLocation(symbol, location);
        }

        return null;
    }
}
