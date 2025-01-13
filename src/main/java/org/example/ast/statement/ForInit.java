package org.example.ast.statement;

import org.example.ast.expression.ExpressionList;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class ForInit implements ISemanticallyAnalyzable {
    public final ExpressionList mExpressionList;
    public final LocalVariableDeclaration mLocalVariableDeclaration;

    public ForInit(ExpressionList expressionList, LocalVariableDeclaration localVariableDeclaration) {
        mExpressionList = expressionList;
        mLocalVariableDeclaration = localVariableDeclaration;
    }

    public boolean hasExpressionList() {
        return mExpressionList != null;
    }

    public boolean hasLocalVariableDeclaration() {
        return mLocalVariableDeclaration != null;
    }

    @Override
    public String toString() {
        return "ForInit: "
                + "ExpressionList: "
                + mExpressionList
                + ", LocalVariableDeclaration: "
                + mLocalVariableDeclaration;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        if (hasLocalVariableDeclaration()) {
            mLocalVariableDeclaration.collectData(currentScope);
        }
    }
}
