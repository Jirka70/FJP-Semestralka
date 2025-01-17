package org.example.ast.statement;

import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.util.Location;

import java.util.Arrays;

public class KeywordStatement extends AbstractStatement {
    @Override
    public void analyze(AbstractScope abstractScope) {

    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    public enum StatementKeywordType {
        BREAK,
        CONTINUE,
    }

    public final StatementKeywordType mType;

    public KeywordStatement(StatementKeywordType type, Location location) {
        super(StatementType.KEYWORD, location);
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
