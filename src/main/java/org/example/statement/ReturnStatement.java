package org.example.statement;

import org.example.primitive.expression.AbstractExpression;

public class ReturnStatement extends AbstractStatement {
    public final AbstractExpression mExpression;

    public ReturnStatement(AbstractExpression expression) {
        super(StatementType.RETURN);
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "Return Statement: " + mExpression;
    }
}
