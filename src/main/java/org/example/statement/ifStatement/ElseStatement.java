package org.example.statement.ifStatement;

import org.example.primitive.block.Block;
import org.example.statement.AbstractStatement;
import org.example.statement.StatementType;

public class ElseStatement extends AbstractStatement {
    public final Block mBody;

    public ElseStatement(Block body) {
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