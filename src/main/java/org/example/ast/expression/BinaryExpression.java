package org.example.ast.expression;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class BinaryExpression extends AbstractExpression {
    public final AbstractExpression mLeftExpression;
    public final AbstractExpression mRightExpression;

    public BinaryExpression(AbstractExpression leftExpression,
                             AbstractExpression rightExpression, ExpressionType operator) {
        super(operator);
        mLeftExpression = leftExpression;
        mRightExpression = rightExpression;
    }

    @Override
    public String toString() {
        return mLeftExpression.toString() + mExpressionType.toString() + mRightExpression.toString();
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {

    }
}
