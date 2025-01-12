package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.ExpressionList;

public class ForLoopControl {
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
}
