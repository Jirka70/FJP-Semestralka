package org.example.primitive.expression;

import java.util.ArrayList;
import java.util.List;

public class MethodCallExpression extends AbstractExpression {
    public final String mName;
    public final List<AbstractExpression> mArgs = new ArrayList<>();

    public MethodCallExpression(String name, List<AbstractExpression> args) {
        super(ExpressionType.METHOD_CALL);
        mName = name;
        mArgs.addAll(args);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(mName + "(");
        for (AbstractExpression arg : mArgs)
            result.append(arg.toString()).append(", ");
        result.append(")");
        return result.toString();
    }
}
