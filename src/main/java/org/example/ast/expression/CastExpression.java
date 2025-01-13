package org.example.ast.expression;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class CastExpression extends UnaryExpression {
    public final String mTypeType;

    public CastExpression(AbstractExpression expression, String typeType) {
        super(expression, ExpressionType.CAST);
        this.mTypeType = typeType;
    }

    @Override
    public String toString() {
        return "(" + mTypeType + ")" + mExpression.toString();
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {

    }
}
