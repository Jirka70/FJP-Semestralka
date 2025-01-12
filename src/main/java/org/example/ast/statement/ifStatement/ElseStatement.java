package org.example.ast.statement.ifStatement;

import org.example.ast.statement.AbstractStatement;
import org.example.ast.statement.StatementType;

public class ElseStatement extends AbstractStatement {
    public final AbstractStatement mBody;

    public ElseStatement(AbstractStatement body) {
        super(StatementType.ELSE);
        mBody = body;
    }

    @Override
    public String toString() {
        return "else {"
                + mBody
                + "}";
    }
}