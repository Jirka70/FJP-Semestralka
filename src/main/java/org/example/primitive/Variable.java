package org.example.primitive;

import org.example.primitive.expression.AbstractExpression;
import org.example.primitive.variable.Modifier;

public class Variable {
    private static final String FINAL_KEYWORD = "finalis";

    public final Modifier mModifier;
    public final String mName;
    public final AbstractExpression mExpression;

    /**
     * Class representing variable in the source code.
     * @param modifier - stands for modifier of variable. If null, variable has no modifiers
     * */
    public Variable(Modifier modifier, String name, AbstractExpression expression) {
        mModifier = modifier;
        mName = name;
        mExpression = expression;
    }

    public boolean isFinal() {
        return mModifier.mModifier.equals(FINAL_KEYWORD);
    }

    public boolean hasModifier() {
        return mModifier != null;
    }
}
