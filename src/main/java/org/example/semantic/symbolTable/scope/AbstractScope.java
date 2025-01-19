package org.example.semantic.symbolTable.scope;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.util.Location;

import java.util.*;

public abstract class AbstractScope {
    private static final Set<String> mDefinedDataTypes = new HashSet<>();

    public final AbstractDescriptor mScopeDescriptor;
    public final AbstractScope mParentAbstractScope;
    public final Map<AbstractSymbol, AbstractScope> mChildrenScopes = new HashMap<>();

    static {
        mDefinedDataTypes.add("vacuum");
        mDefinedDataTypes.add("int");
        mDefinedDataTypes.add("float");
        mDefinedDataTypes.add("char");
        mDefinedDataTypes.add("boolean");
        mDefinedDataTypes.add("String");
        mDefinedDataTypes.add("nullus");
    }

    public AbstractScope(AbstractScope parentScope, AbstractDescriptor descriptor) {
        mParentAbstractScope = parentScope;
        mScopeDescriptor = descriptor;

    }

    public void addChildScope(AbstractSymbol symbol, AbstractScope abstractScope) {
        mChildrenScopes.put(symbol, abstractScope);
    }

    public AbstractScope getChildScopeBySymbol(AbstractSymbol symbol) {
        return mChildrenScopes.get(symbol);
    }

    public boolean isTypeDefined(AbstractSymbol symbol) {
        return mDefinedDataTypes.contains(symbol.mName);
    }

    public boolean hasParent() {
        return mParentAbstractScope != null;
    }

    public abstract boolean isSymbolDefinedOnLocation(AbstractSymbol symbol, Location location);

    public abstract void addSymbol(AbstractSymbol symbol, AbstractDescriptor descriptor, Location location);

    public abstract boolean containsSymbolInScopeOnLocation(AbstractSymbol symbol, Location location);

    public abstract AbstractDescriptor getSymbolDescriptorOnLocation(AbstractSymbol symbol, Location location);
    public abstract Collection<AbstractSymbol> getAllSymbols();
    public abstract Collection<AbstractDescriptor> getAllDescriptors();
}
