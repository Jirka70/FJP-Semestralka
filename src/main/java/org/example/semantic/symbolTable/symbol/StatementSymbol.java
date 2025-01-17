package org.example.semantic.symbolTable.symbol;

import org.example.util.Location;

import java.util.Objects;

public class StatementSymbol extends AbstractSymbol {
    public final Location mLocation;

    public StatementSymbol(String name, Location location) {
        super(name);
        mLocation = location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), mName, mLocation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatementSymbol other) {
            return other.mLocation.equals(mLocation) && other.mName.equals(mName);
        }

        return false;
    }
}
