package org.example.ast.expression;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.BooleanType;
import org.example.util.Location;

public class TernaryExpression extends AbstractExpression {
    public final AbstractExpression mCondition;
    public final AbstractExpression mFirst;
    public final AbstractExpression mSecond;

    public TernaryExpression(AbstractExpression condition, AbstractExpression trueBranch,
                             AbstractExpression falseBranch, Location location) {
        super(ExpressionType.TERNARY, location);
        mCondition = condition;
        mFirst = trueBranch;
        mSecond = falseBranch;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        AbstractType conditionType = mCondition.evaluateType(abstractScope);
        if (!(conditionType instanceof BooleanType)) {
            throw new TypeMismatchException("Ternary expression expects a boolean type in condition " + mLocation);
        }

        AbstractType trueBranchType = mFirst.evaluateType(abstractScope);
        AbstractType falseBranchType = mSecond.evaluateType(abstractScope);

        if (!trueBranchType.isCompatibleWith(falseBranchType)) {
            throw new TypeMismatchException("Ternary expression expects two compatible types in both branches. On "
                    + mLocation);
        }

        return trueBranchType.combineWith(falseBranchType);
    }

    @Override
    public String toString() {
        return mCondition.toString() + "?" + mFirst.toString() + ":" + mSecond.toString();
    }

    @Override
    public void analyze(AbstractScope abstractScope) {
        
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }
}
