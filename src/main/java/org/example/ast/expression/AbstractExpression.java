package org.example.ast.expression;

import org.example.semantic.ISemanticallyAnalyzable;

public abstract class AbstractExpression implements ISemanticallyAnalyzable {
    public final ExpressionType mExpressionType;

    protected AbstractExpression(ExpressionType expressionType) {
        mExpressionType = expressionType;
    }

    @Override
    public abstract String toString();
}
