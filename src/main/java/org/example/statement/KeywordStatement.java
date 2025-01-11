package org.example.statement;

import java.util.Arrays;

public class KeywordStatement extends AbstractStatement {
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
