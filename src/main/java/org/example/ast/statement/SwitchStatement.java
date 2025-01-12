package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;

import java.util.ArrayList;
import java.util.List;

public class SwitchStatement extends AbstractStatement {
    public final AbstractExpression mExpression;
    public final List<SwitchCase> mCases = new ArrayList<>();

    public SwitchStatement(AbstractExpression expression, List<SwitchCase> cases) {
        super(StatementType.SWITCH);
        mExpression = expression;
        if (cases == null || cases.size() == 0)
            throw new IllegalArgumentException("Switch statement must have at least one switch case");
        mCases.addAll(cases);
    }

    @Override
    public String toString() {
        return "Switch Statement: " + mExpression + " " + mCases;
    }
}
