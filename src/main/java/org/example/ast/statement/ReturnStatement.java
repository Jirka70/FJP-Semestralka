package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class ReturnStatement extends AbstractStatement {
    public final AbstractExpression mExpression;

    public ReturnStatement(AbstractExpression expression) {
        super(StatementType.RETURN);
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "Return Statement: " + mExpression;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {

    }
}
