package org.example.statement;

import org.example.primitive.expression.AbstractExpression;

import java.util.ArrayList;
import java.util.List;

public class LocalVariable extends AbstractBlockStatement {
    public final List<String> mModifiers = new ArrayList<>();
    public final String mName;
    public final String mType;
    public final AbstractExpression mValue;

    public LocalVariable(List<String> modifiers, String name, String type, AbstractExpression value) {
        super();
        mModifiers.addAll(modifiers);
        mName = name;
        mType = type;
        mValue = value;
    }

    @Override
    public String toString() {
        return "LocalVariable: Modifiers: "
                + mModifiers
                + ", Name: "
                + mName
                + ", Type: "
                + mType
                +", Value: "
                + mValue;
    }
}
