package org.example.ast.expression;

import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class ExpressionList implements ISemanticallyAnalyzable {
    public final List<AbstractExpression> mExpressions = new ArrayList<>();

    public ExpressionList(List<AbstractExpression> expressions) {
        mExpressions.addAll(expressions);
    }

    @Override
    public String toString() {
        return "ExpressionList: " + mExpressions;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {
        for (AbstractExpression expression : mExpressions) {
            expression.analyze(symbolTable);
        }
    }

    @Override
    public void collectData(Scope currentScope) {
        for (AbstractExpression expression : mExpressions) {
            expression.collectData(currentScope);
        }
    }
}
