package org.example.semantic.symbolTable.scope;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.scope.table.BlockScopeTable;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.util.Location;

import java.util.Collection;

public class BlockScope extends AbstractScope {

    public final BlockScopeTable mBlockScopeTable = new BlockScopeTable();

    public BlockScope(AbstractScope parentScope, AbstractDescriptor descriptor) {
        super(parentScope, descriptor);
    }

    @Override
    public boolean isSymbolDefinedOnLocation(AbstractSymbol symbol, Location location) {
        if (mBlockScopeTable.isSymbolDefinedOnLocation(symbol, location)) {
            return true;
        }

        if (hasParent()) {
            return mParentAbstractScope.isSymbolDefinedOnLocation(symbol, location);
        }

        return false;
    }

    @Override
    public void addSymbol(AbstractSymbol symbol, AbstractDescriptor descriptor, Location location) {
        mBlockScopeTable.addSymbol(symbol, descriptor, location);
    }

    @Override
    public boolean containsSymbolInScopeOnLocation(AbstractSymbol symbol, Location location) {
        return mBlockScopeTable.isSymbolDefinedOnLocation(symbol, location);
    }

    @Override
    public AbstractDescriptor getSymbolDescriptorOnLocation(AbstractSymbol symbol, Location location) {
        if (mBlockScopeTable.isSymbolDefinedOnLocation(symbol, location)) {
            return mBlockScopeTable.getDescriptor(symbol);
        }

        if (hasParent()) {
            return mParentAbstractScope.getSymbolDescriptorOnLocation(symbol, location);
        }

        return null;
    }

    @Override
    public Collection<AbstractSymbol> getAllSymbols() {
        return mBlockScopeTable.getAllSymbols();
    }
}
