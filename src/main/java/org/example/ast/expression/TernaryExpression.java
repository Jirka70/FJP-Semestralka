package org.example.ast.expression;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public class TernaryExpression extends AbstractExpression {
    public final AbstractExpression mCondition;
    public final AbstractExpression mFirst;
    public final AbstractExpression mSecond;

    public TernaryExpression(AbstractExpression condition, AbstractExpression trueBranch,
                             AbstractExpression falseBranch) {
        super(ExpressionType.TERNARY);
        mCondition = condition;
        mFirst = trueBranch;
        mSecond = falseBranch;
    }

    @Override
    public String toString() {
        return mCondition.toString() + "?" + mFirst.toString() + ":" + mSecond.toString();
    }

    @Override
    public void analyze(SymbolTable symbolTable) {
        
    }

    @Override
    public void collectData(Scope currentScope) {

    }
}
