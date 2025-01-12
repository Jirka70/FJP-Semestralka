package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;

import java.util.ArrayList;
import java.util.List;

public class SwitchCase {
    public final AbstractExpression mExpression;
    public final List<AbstractBlockStatement> mBody = new ArrayList<>();

    public SwitchCase(AbstractExpression expression, List<AbstractBlockStatement> body) {
        mExpression = expression;
        if (body != null)
            mBody.addAll(body);
    }

    @Override
    public String toString() {
        return "Switch Case: " + mExpression + " " + mBody;
    }
}
