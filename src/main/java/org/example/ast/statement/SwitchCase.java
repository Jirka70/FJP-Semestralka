package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class SwitchCase implements ISemanticallyAnalyzable {
    private static final String KEYWORD_CASE = "case";
    public final AbstractExpression mExpression; // null means default case
    public final List<AbstractBlockStatement> mBody = new ArrayList<>();
    public final Location mLocation;

    public SwitchCase(AbstractExpression expression, List<AbstractBlockStatement> body, Location location) {
        mExpression = expression;
        if (body != null)
            mBody.addAll(body);
        mLocation = location;
    }

    @Override
    public String toString() {
        return "Switch Case: " + mExpression + " " + mBody;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        if (mExpression != null) {
            mExpression.analyze(abstractScope);
        }

        for (AbstractBlockStatement blockStatement : mBody) {
            if (blockStatement == null) {
                continue;
            }
            blockStatement.analyze(abstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        for (AbstractBlockStatement blockStatement : mBody) {
            if (blockStatement == null) {
                continue;
            }
            blockStatement.collectData(currentAbstractScope);
        }
    }
}
