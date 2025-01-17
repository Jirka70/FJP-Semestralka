package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.WhileLoopDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
import org.example.util.Location;


public class WhileStatement extends AbstractStatement {
    private static final String WHILE_KEYWORD = "while";
    public final AbstractExpression mExpression;
    public final AbstractStatement mBody;

    public WhileStatement(AbstractExpression expression, AbstractStatement body, Location location) {
        super(StatementType.WHILE, location);
        mExpression = expression;
        mBody = body;
    }

    @Override
    public String toString() {
        return "while (" + mExpression + ") " +  mBody;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractSymbol symbol = new StatementSymbol(WHILE_KEYWORD, mLocation);

        AbstractScope whileAbstractScope = abstractScope.getChildScopeBySymbol(symbol);

        if (whileAbstractScope == null) {
            throw new InvalidStatementException("While statement was not found on location " + mLocation);
        }

        mBody.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor abstractDescriptor = new WhileLoopDescriptor();
        AbstractScope whileAbstractScope = new BlockScope(currentAbstractScope, abstractDescriptor);
        AbstractSymbol whileSymbol = new StatementSymbol(WHILE_KEYWORD, mLocation);
        currentAbstractScope.addChildScope(whileSymbol, whileAbstractScope);

        mBody.collectData(whileAbstractScope);
    }
}
