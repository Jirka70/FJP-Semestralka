package org.example.primitive;

import org.example.primitive.expression.AbstractExpression;

public class ForLoop {
    public final AbstractExpression mInit;
    public final AbstractExpression mEnd;
    public final AbstractExpression mUpdate;

    public ForLoop(AbstractExpression init, AbstractExpression end, AbstractExpression update) {
        mInit = init;
        mEnd = end;
        mUpdate = update;
    }
}
