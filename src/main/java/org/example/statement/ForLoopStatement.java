package org.example.statement;

import org.example.primitive.expression.AbstractExpression;

public class ForLoopStatement {
    public final AbstractExpression mInit;
    public final AbstractExpression mEnd;
    public final AbstractExpression mUpdate;

    public ForLoopStatement(AbstractExpression init, AbstractExpression end, AbstractExpression update) {
        mInit = init;
        mEnd = end;
        mUpdate = update;
    }
}
