package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.WhileLoopDescriptor;
import org.example.semantic.symbolTable.scope.Scope;


public class WhileStatement extends AbstractStatement {
    public final AbstractExpression mExpression;
    public final AbstractStatement mBody;

    public WhileStatement(AbstractExpression expression, AbstractStatement body) {
        super(StatementType.WHILE);
        mExpression = expression;
        mBody = body;
    }

    @Override
    public String toString() {
        return "while (" + mExpression + ") " +  mBody;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {
        mBody.analyze(symbolTable);
    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor abstractDescriptor = new WhileLoopDescriptor();
        Scope whileScope = new Scope(currentScope, abstractDescriptor);
        currentScope.addChildScope(whileScope);

        mBody.collectData(whileScope);
    }
}
