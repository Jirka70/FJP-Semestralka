package org.example.ast.statement;

import org.example.codeGeneration.IGeneratable;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.util.Location;

public abstract class AbstractBlockStatement implements ISemanticallyAnalyzable, IGeneratable {
    public final Location mLocation;

    protected AbstractBlockStatement(Location location) {
        mLocation = location;
    }

    @Override
    public abstract String toString();

}
