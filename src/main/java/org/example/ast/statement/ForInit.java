package org.example.ast.statement;

import org.example.ast.expression.ExpressionList;

public class ForInit {
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
}
