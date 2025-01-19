package org.example.ast.statement;

import org.example.ast.LocalVariable;
import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.BinaryExpression;
import org.example.ast.expression.ExpressionType;
import org.example.ast.expression.IdentifierExpression;
import org.example.codeGeneration.CodeGenerator;
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

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating local variable declaration " + mLocalVariables);
        for (LocalVariable mLocalVariable : mLocalVariables) {
            if (mLocalVariable.isAssigned()) {
                IdentifierExpression lhs = new IdentifierExpression(mLocalVariable.mName, mLocalVariable.mLocation);
                AbstractExpression rhs = mLocalVariable.mExpression;
                BinaryExpression assignExpr = new BinaryExpression(lhs, rhs, ExpressionType.ASSIGN, mLocalVariable.mLocation);
                assignExpr.generate(currentAbstractScope, generator);

                int variableSize = generator.typeSize(mLocalVariable.mDeclaredType);
                generator.addInstruction("INT 0 " + (-variableSize)); // ignore assignment return value
            }
        }
    }
}
