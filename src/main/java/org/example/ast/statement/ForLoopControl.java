package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.ExpressionList;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class ForLoopControl implements ISemanticallyAnalyzable {
    public final ForInit mInit;
    public final AbstractExpression mEnd;
    public final ExpressionList mUpdate;

    public ForLoopControl(ForInit init, AbstractExpression end, ExpressionList update) {
        mInit = init;
        mEnd = end;
        mUpdate = update;
    }

    @Override
    public String toString() {
        return "for (" + mInit + ", " + mEnd + ", " + mUpdate + ")";
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        mInit.collectData(currentScope);
        mEnd.collectData(currentScope);
        mUpdate.collectData(currentScope);
    }
}
