package org.example.ast.statement;


import org.example.util.Location;

public abstract class AbstractStatement extends AbstractBlockStatement {
    public final StatementType mStatementType;

    public AbstractStatement(StatementType statementType, Location location) {
        super(location);
        mStatementType = statementType;
    }

}
