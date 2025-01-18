package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.ExpressionList;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.util.Location;

public class ForLoopControl implements ISemanticallyAnalyzable {
    public final ForInit mInit;
    public final AbstractExpression mEnd;
    public final ExpressionList mUpdate;
    public final Location mLocation;

    public ForLoopControl(ForInit init, AbstractExpression end, ExpressionList update, Location location) {
        mInit = init;
        mEnd = end;
        mUpdate = update;
        mLocation = location;
    }

    @Override
    public String toString() {
        return "for (" + mInit + ", " + mEnd + ", " + mUpdate + ")";
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        if (mInit != null) {
            mInit.analyze(abstractScope);
        }

        if (mEnd != null) {
            mEnd.analyze(abstractScope);
        }

        if (mUpdate != null) {
            mUpdate.analyze(abstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        if (mInit != null) {
            mInit.collectData(currentAbstractScope);
        }

        if (mEnd != null) {
            mEnd.collectData(currentAbstractScope);
        }

        if (mUpdate != null) {
            mUpdate.collectData(currentAbstractScope);
        }
    }
}
