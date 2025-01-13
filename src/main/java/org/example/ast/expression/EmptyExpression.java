package org.example.ast.expression;


import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class EmptyExpression extends AbstractExpression {

    public EmptyExpression() {
        super(ExpressionType.EMPTY);
    }

    @Override
    public String toString() {
        return "EmptyExpression";
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {

    }
}
