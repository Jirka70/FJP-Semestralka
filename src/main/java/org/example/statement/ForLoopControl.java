package org.example.statement;

import org.example.primitive.expression.AbstractExpression;
import org.example.primitive.expression.ExpressionList;

public class ForLoopControl {
    public final LocalVariableDeclaration mInit;
    public final AbstractExpression mEnd;
    public final ExpressionList mUpdate;

    public ForLoopControl(LocalVariableDeclaration init, AbstractExpression end, ExpressionList update) {
        mInit = init;
        mEnd = end;
        mUpdate = update;
    }

    @Override
    public String toString() {
        return "for (" + mInit + ", " + mEnd + ", " + mUpdate + ")";
    }
}
