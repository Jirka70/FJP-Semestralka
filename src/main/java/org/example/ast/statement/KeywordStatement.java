package org.example.ast.statement;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.Arrays;

public class KeywordStatement extends AbstractStatement {
    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {

    }

    public enum StatementKeywordType {
        BREAK,
        CONTINUE,
    }

    public final StatementKeywordType mType;

    public KeywordStatement(StatementKeywordType type) {
        super(StatementType.KEYWORD);
        if (type == null)
            throw new IllegalArgumentException("Keyword statement must be one of " +
                    Arrays.toString(StatementKeywordType.values()));
        mType = type;
    }

    @Override
    public String toString() {
        return mType.toString();
    }
}
