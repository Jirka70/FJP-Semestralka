package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;


public class DoWhileStatement extends AbstractStatement {
    public final AbstractStatement mBody;
    public final AbstractExpression mExpression;

    public DoWhileStatement(AbstractStatement body, AbstractExpression expression) {
        super(StatementType.DO_WHILE);
        mBody = body;
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "do " +
                mBody +
                " while " + "(" + mExpression + ");";
    }
}
