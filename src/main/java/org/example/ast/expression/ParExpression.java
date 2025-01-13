package org.example.ast.expression;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class ParExpression extends PrimaryExpression {
    public final AbstractExpression mExpression;

    public ParExpression(AbstractExpression expression) {
        super(ExpressionType.PARENTHESES);
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "(" + mExpression.toString() + ")";
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {

    }
}
