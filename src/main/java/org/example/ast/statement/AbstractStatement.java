package org.example.ast.statement;


public abstract class AbstractStatement extends AbstractBlockStatement {
    public final StatementType mStatementType;

    public AbstractStatement(StatementType statementType) {
        mStatementType = statementType;
    }

}
