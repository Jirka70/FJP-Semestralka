package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.scope.Scope;


public class DoWhileStatement extends AbstractStatement {
    public final AbstractStatement mBody;
    public final AbstractExpression mExpression;

    public DoWhileStatement(AbstractStatement body, AbstractExpression expression) {
        super(StatementType.DO_WHILE);
        mBody = body;
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "do " +
                mBody +
                " while " + "(" + mExpression + ");";
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor doWhileDescriptor = new DoWhileDescriptor();
        Scope doWhileScope = new Scope(currentScope, doWhileDescriptor);
        currentScope.addChildScope(doWhileScope);
        mBody.collectData(doWhileScope);
    }
}
