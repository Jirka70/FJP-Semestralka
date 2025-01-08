package org.example.primitive.expression;

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
}
