package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.util.Location;

public class ExpressionStatement extends AbstractStatement {
    public final AbstractExpression mExpression;

    public ExpressionStatement(AbstractExpression expression, Location location) {
        super(StatementType.EXPRESSION, location);
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "Expression Statement: " + mExpression;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        mExpression.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }
}
