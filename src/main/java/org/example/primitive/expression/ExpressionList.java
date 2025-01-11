package org.example.primitive.expression;

import java.util.ArrayList;
import java.util.List;

public class ExpressionList {
    public final List<AbstractExpression> mExpressions = new ArrayList<>();

    public ExpressionList(List<AbstractExpression> expressions) {
        mExpressions.addAll(expressions);
    }

    @Override
    public String toString() {
        return "ExpressionList: " + mExpressions;
    }
}
