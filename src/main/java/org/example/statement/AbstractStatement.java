package org.example.statement;


public abstract class AbstractStatement extends AbstractBlockStatement {
    public final StatementType mStatementType;

    public AbstractStatement(StatementType statementType) {
        mStatementType = statementType;
    }

    @Override
    public abstract String toString();
}
