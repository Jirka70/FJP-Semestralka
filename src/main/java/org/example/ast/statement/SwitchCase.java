package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.CaseDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
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
        AbstractSymbol symbol = new StatementSymbol(KEYWORD_CASE, mLocation);
        AbstractScope caseAbstractScope = abstractScope.getChildScopeBySymbol(symbol);

        if (mExpression != null) {
            mExpression.analyze(abstractScope);
        }

        if (caseAbstractScope == null) {
            throw new InvalidStatementException("Case statement was not found on location " + mLocation);
        }

        for (AbstractBlockStatement blockStatement : mBody) {
            if (blockStatement == null) {
                continue;
            }
            blockStatement.analyze(caseAbstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor caseDescriptor = new CaseDescriptor();
        AbstractScope caseAbstractScope = new BlockScope(currentAbstractScope, caseDescriptor);
        AbstractSymbol switchSymbol = new StatementSymbol(KEYWORD_CASE, mLocation);
        currentAbstractScope.addChildScope(switchSymbol, caseAbstractScope);

        for (AbstractBlockStatement blockStatement : mBody) {
            if (blockStatement == null) {
                continue;
            }
            blockStatement.collectData(caseAbstractScope);
        }
    }
}
