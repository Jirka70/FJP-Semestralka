package org.example.statement;

import org.example.primitive.expression.ExpressionList;

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
