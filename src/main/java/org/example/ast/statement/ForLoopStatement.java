package org.example.ast.statement;

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
