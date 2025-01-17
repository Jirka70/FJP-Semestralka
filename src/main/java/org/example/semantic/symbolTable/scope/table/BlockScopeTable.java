package org.example.semantic.symbolTable.scope.table;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.util.Location;

import java.util.HashMap;
import java.util.Map;

public class BlockScopeTable {
    public final Map<AbstractSymbol, SymbolDescriptor> mScopeTable = new HashMap<>();

    public void addSymbol(AbstractSymbol symbol, AbstractDescriptor descriptor, Location location) {
        SymbolDescriptor symbolDescriptor = new SymbolDescriptor(descriptor, location);
        mScopeTable.put(symbol, symbolDescriptor);
    }

    public boolean isSymbolDefinedOnLocation(AbstractSymbol symbol, Location location) {
        SymbolDescriptor symbolDescriptor = mScopeTable.get(symbol);

        if (symbolDescriptor == null) {
            return false;
        }

        Location symbolLocation = symbolDescriptor.mLocation;

        if (symbolLocation.mLine < location.mLine) {
            return true;
        } else if (symbolLocation.mLine > location.mLine) {
            return false;
        }

        return symbolLocation.mCharPositionInLine < location.mCharPositionInLine;
    }

    public AbstractDescriptor getDescriptor(AbstractSymbol symbol) {
        SymbolDescriptor descriptor = mScopeTable.get(symbol);

        if (descriptor == null) {
            return null;
        }

        return descriptor.mDescriptor;
    }

    public boolean containsSymbolInCurrentScope(AbstractSymbol symbol) {
        return mScopeTable.containsKey(symbol);
    }

    public static class SymbolDescriptor {
        public final AbstractDescriptor mDescriptor;
        public final Location mLocation;

        public SymbolDescriptor(AbstractDescriptor descriptor, Location location) {
            mDescriptor = descriptor;
            mLocation = location;
        }
    }
}
