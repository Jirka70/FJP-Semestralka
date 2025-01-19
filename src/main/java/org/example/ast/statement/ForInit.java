package org.example.ast.statement;

import org.example.ast.expression.ExpressionList;
import org.example.codeGeneration.CodeGenerator;
import org.example.codeGeneration.IGeneratable;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;

public class ForInit implements ISemanticallyAnalyzable, IGeneratable {
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
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        if (hasLocalVariableDeclaration()) {
            mLocalVariableDeclaration.analyze(abstractScope);
        }

        if (hasExpressionList()) {
            mExpressionList.analyze(abstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        if (hasLocalVariableDeclaration()) {
            mLocalVariableDeclaration.collectData(currentAbstractScope);
        }

        if (hasExpressionList()) {
            mExpressionList.collectData(currentAbstractScope);
        }
    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        if (hasLocalVariableDeclaration()) {
            mLocalVariableDeclaration.generate(currentAbstractScope, generator);
        }

        if (hasExpressionList()) {
            mExpressionList.generate(currentAbstractScope, generator);
        }
    }
}
