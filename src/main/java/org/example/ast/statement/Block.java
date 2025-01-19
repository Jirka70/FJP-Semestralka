package org.example.ast.statement;

import org.example.codeGeneration.CodeGenerator;
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
            if (blockStatement == null) { // e.g. two semicolons in row
                continue;
            }

            blockStatement.analyze(abstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        for (AbstractBlockStatement blockStatement : mBlockStatements) {
            if (blockStatement == null) { // e.g. two semicolons in row
                continue;
            }

            blockStatement.collectData(currentAbstractScope);
        }
    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        for (AbstractBlockStatement blockStatement : mBlockStatements) {
            if (blockStatement == null) { // e.g. two semicolons in row
                continue;
            }

            blockStatement.generate(currentAbstractScope, generator);
        }
    }
}
