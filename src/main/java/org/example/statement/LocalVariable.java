package org.example.statement;

import org.example.primitive.expression.AbstractExpression;

public class LocalVariable extends AbstractBlockStatement {
    public final String mName;
    public final AbstractExpression mValue;

    public LocalVariable(String name, AbstractExpression value) {
        super();
        mName = name;
        mValue = value;
    }

    @Override
    public String toString() {
        return "LocalVariable: "
                + ", Name: "
                + mName
                +", Value: "
                + mValue;
    }
}
