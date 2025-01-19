package org.example.ast.statement;

import org.example.codeGeneration.CodeGenerator;
import org.example.codeGeneration.IGeneratable;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.util.Location;

public abstract class AbstractBlockStatement implements ISemanticallyAnalyzable, IGeneratable {
    public final Location mLocation;

    protected AbstractBlockStatement(Location location) {
        mLocation = location;
    }

    @Override
    public abstract String toString();

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        throw new RuntimeException("Generate not implemented for " + this);
        // TODO: smazat až bude všude implementováno
    }
}
