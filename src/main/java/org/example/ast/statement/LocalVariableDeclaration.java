package org.example.ast.statement;

import org.example.ast.LocalVariable;

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
}
