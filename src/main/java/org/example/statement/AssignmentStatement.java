package org.example.statement;

import org.example.primitive.Variable;
import org.example.primitive.expression.AbstractExpression;

public class AssignmentStatement extends AbstractStatement {
    public final Variable mVariable;
    public final AbstractExpression mExpression;

    public AssignmentStatement(Variable variable, AbstractExpression expression) {
        super(StatementType.ASSIGNMENT);
        mVariable = variable;
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "Assignment Statement: " + mExpression;
    }
}
