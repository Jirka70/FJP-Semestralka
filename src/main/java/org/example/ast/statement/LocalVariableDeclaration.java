package org.example.ast.statement;

import org.example.ast.LocalVariable;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class LocalVariableDeclaration extends AbstractBlockStatement {
    public final List<LocalVariable> mLocalVariables = new ArrayList<>();

    public LocalVariableDeclaration(List<LocalVariable> localVariables) {
        if (localVariables == null || localVariables.isEmpty())
            throw new IllegalArgumentException("Local variable declaration must declare at least 1 local variable");
        mLocalVariables.addAll(localVariables);
    }

    @Override
    public String toString() {
        return "LocalVariableDeclaration: " + mLocalVariables;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        for (LocalVariable localVariable : mLocalVariables) {
            localVariable.collectData(currentScope);
        }
    }
}
