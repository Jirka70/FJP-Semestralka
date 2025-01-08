package org.example.primitive;

import org.example.primitive.expression.AbstractExpression;

import java.util.ArrayList;
import java.util.List;

public class Variable {
    private static final String FINAL_KEYWORD = "finalis";

    public final List<String> mModifiers = new ArrayList<>();
    public final String mDeclaredType;
    public final String mName;
    public final AbstractExpression mExpression;

    /**
     * Class representing variable in the source code.
     * @param modifiers - stands for modifiers of variable. If empty, variable has no modifiers
     * @param expression - RHS of assignment operator
     * */
    public Variable(List<String> modifiers, String type, String name, AbstractExpression expression) {
        if (modifiers != null) {
            mModifiers.addAll(modifiers);
        }
        mDeclaredType = type;
        mName = name;
        mExpression = expression;
    }

    public boolean isFinal() {
        return mModifiers.contains(FINAL_KEYWORD);
    }

    public boolean hasModifier() {
        return mModifiers.isEmpty();
    }

    @Override
    public String toString() {
        return  mModifiers + " " + mDeclaredType + " " + mName + (mExpression != null ? " = " + mExpression : "");
    }
}
