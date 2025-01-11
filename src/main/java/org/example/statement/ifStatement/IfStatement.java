package org.example.statement.ifStatement;

import org.example.primitive.expression.AbstractExpression;
import org.example.statement.AbstractStatement;
import org.example.statement.StatementType;


public class IfStatement extends AbstractStatement {
    public final AbstractExpression mExpression;
    public final ElseStatement mElseStatement;
    public final AbstractStatement mBody;

    public IfStatement(AbstractExpression expression, AbstractStatement body, ElseStatement elseStatement) {
        super(StatementType.IF);
        mExpression = expression;
        mElseStatement = elseStatement;
        mBody = body;
    }

    public boolean hasElse() {
        return mElseStatement != null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("if (")
                .append(mExpression)
                .append(") {")
                .append(mBody)
                .append("}");
        if (hasElse()) {
            builder.append(" ").append(mElseStatement);
        }

        return builder.toString();
    }
}
