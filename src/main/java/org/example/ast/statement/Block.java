package org.example.ast.statement;

import java.util.ArrayList;
import java.util.List;

public class Block extends AbstractStatement {
    public final List<AbstractBlockStatement> mBlockStatements = new ArrayList<>();

    public Block(List<AbstractBlockStatement> blockStatements) {
        super(StatementType.BLOCK);
        if (blockStatements != null)
            mBlockStatements.addAll(blockStatements);
    }

    @Override
    public String toString() {
        return "Block: " + mBlockStatements;
    }
}
