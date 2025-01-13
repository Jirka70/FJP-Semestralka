package org.example.ast.expression;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class PrefixExpression extends UnaryExpression {
    public PrefixExpression(AbstractExpression expression, ExpressionType operand) {
        super(expression, operand);
    }

    @Override
    public String toString() {
        return mExpressionType.toString() + mExpression.toString();
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {

    }
}