package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;

public class ExpressionStatement extends AbstractStatement {
    public final AbstractExpression mExpression;

    public ExpressionStatement(AbstractExpression expression) {
        super(StatementType.EXPRESSION);
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "Expression Statement: " + mExpression;
    }
}
