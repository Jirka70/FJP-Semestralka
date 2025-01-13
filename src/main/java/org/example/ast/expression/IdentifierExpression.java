package org.example.ast.expression;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class IdentifierExpression extends PrimaryExpression {
    public final String mIdentifier;

    public IdentifierExpression(String identifier) {
        super(ExpressionType.IDENTIFIER);
        mIdentifier = identifier;
    }

    @Override
    public String toString() {
        return mIdentifier;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {

    }
}
