package org.example.ast.statement;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class Block extends AbstractStatement {
    public final List<AbstractBlockStatement> mBlockStatements = new ArrayList<>();

    public Block(List<AbstractBlockStatement> blockStatements, Location location) {
        super(StatementType.BLOCK, location);
        if (blockStatements != null)
            mBlockStatements.addAll(blockStatements);
    }

    @Override
    public String toString() {
        return "Block: " + mBlockStatements;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        for (AbstractBlockStatement blockStatement : mBlockStatements) {
            blockStatement.analyze(abstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        for (AbstractBlockStatement blockStatement : mBlockStatements) {
            blockStatement.collectData(currentAbstractScope);
        }
    }
}
