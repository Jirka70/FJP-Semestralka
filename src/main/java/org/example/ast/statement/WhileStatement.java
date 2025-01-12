package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;


public class WhileStatement extends AbstractStatement {
    public final AbstractExpression mExpression;
    public final AbstractStatement mBody;

    public WhileStatement(AbstractExpression expression, AbstractStatement body) {
        super(StatementType.WHILE);
        mExpression = expression;
        mBody = body;
    }

    @Override
    public String toString() {
        return "while (" + mExpression + ") " +  mBody;
    }
}
