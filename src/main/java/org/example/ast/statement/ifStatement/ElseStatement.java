package org.example.ast.statement.ifStatement;

import org.example.ast.statement.AbstractStatement;
import org.example.ast.statement.StatementType;
import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.ElseDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
import org.example.util.Location;

public class ElseStatement extends AbstractStatement {
    private static final String ELSE_KEYWORD = "else";
    public final AbstractStatement mBody;

    public ElseStatement(AbstractStatement body, Location location) {
        super(StatementType.ELSE, location);
        mBody = body;
    }

    @Override
    public String toString() {
        return "else {"
                + mBody
                + "}";
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractSymbol symbol = new StatementSymbol(ELSE_KEYWORD, mLocation);
        AbstractScope elseAbstractScope = abstractScope.getChildScopeBySymbol(symbol);

        if (elseAbstractScope == null) {
            throw new InvalidStatementException("Else statement on location " + mLocation + " was not found");
        }

        mBody.analyze(elseAbstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor elseDescriptor = new ElseDescriptor();
        AbstractScope elseAbstractScope = new BlockScope(currentAbstractScope, elseDescriptor);
        AbstractSymbol elseSymbol = new StatementSymbol(ELSE_KEYWORD, mLocation);
        currentAbstractScope.addChildScope(elseSymbol, elseAbstractScope);
        mBody.collectData(elseAbstractScope);
    }

    @Override
    public void generate(AbstractScope currentScope, CodeGenerator generator) {
        mBody.generate(currentScope, generator);
    }
}