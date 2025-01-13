package org.example.ast.statement;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

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

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        for (AbstractBlockStatement blockStatement : mBlockStatements) {
            blockStatement.collectData(currentScope);
        }
    }
}
