package org.example.statement;

import org.example.primitive.block.Block;
import org.example.primitive.expression.AbstractExpression;

public class ForLoopStatement extends AbstractStatement {
    public final AbstractExpression mInit;
    public final AbstractExpression mEnd;
    public final AbstractExpression mUpdate;
    public final Block mBody;

    public ForLoopStatement(AbstractExpression init, AbstractExpression end, AbstractExpression update, Block body) {
        super(StatementType.FOR);
        mInit = init;
        mEnd = end;
        mUpdate = update;
        mBody = body;
    }

    @Override
    public String toString() {
        return "for (" + mInit + ", " + mEnd + ", " + mUpdate + ") {" + mBody + "}";
    }
}
