package org.example.ast.statement;

import org.example.ast.LocalVariable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class LocalVariableDeclaration extends AbstractBlockStatement {
    public final List<LocalVariable> mLocalVariables = new ArrayList<>();

    public LocalVariableDeclaration(List<LocalVariable> localVariables, Location location) {
        super(location);
        if (localVariables == null || localVariables.isEmpty())
            throw new IllegalArgumentException("Local variable declaration must declare at least 1 local variable");
        mLocalVariables.addAll(localVariables);

    }

    @Override
    public String toString() {
        return "LocalVariableDeclaration: " + mLocalVariables;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        for (LocalVariable localVariable : mLocalVariables) {
            localVariable.analyze(abstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        for (LocalVariable localVariable : mLocalVariables) {
            localVariable.collectData(currentAbstractScope);
        }
    }
}
