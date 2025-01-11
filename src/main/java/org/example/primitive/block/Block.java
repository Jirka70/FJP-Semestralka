package org.example.primitive.block;

import org.example.statement.AbstractBlockStatement;

import java.util.ArrayList;
import java.util.List;

public class Block {
    public final List<AbstractBlockStatement> mBlockStatements = new ArrayList<>();

    public Block(List<AbstractBlockStatement> blockStatements) {
        mBlockStatements.addAll(blockStatements);
    }

    @Override
    public String toString() {
        return "Block: " + mBlockStatements;
    }
}
