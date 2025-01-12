package org.example.statement.ifStatement;

import org.example.statement.AbstractStatement;
import org.example.statement.Block;
import org.example.statement.ForLoopControl;
import org.example.statement.StatementType;

public class ForLoopStatement extends AbstractStatement {
    public final ForLoopControl mForLoopControl;
    public final Block mBody;

    public ForLoopStatement(ForLoopControl forLoopControl, Block body) {
        super(StatementType.FOR);
        mForLoopControl = forLoopControl;
        mBody = body;
    }

    @Override
    public String toString() {
        return mForLoopControl + "{ " + mBody + " }";
    }
}
