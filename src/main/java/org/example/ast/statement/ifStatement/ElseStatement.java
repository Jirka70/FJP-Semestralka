package org.example.ast.statement.ifStatement;

import org.example.ast.statement.AbstractStatement;
import org.example.ast.statement.StatementType;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.ElseDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

public class ElseStatement extends AbstractStatement {
    public final AbstractStatement mBody;

    public ElseStatement(AbstractStatement body) {
        super(StatementType.ELSE);
        mBody = body;
    }

    @Override
    public String toString() {
        return "else {"
                + mBody
                + "}";
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor elseDescriptor = new ElseDescriptor();
        Scope elseScope = new Scope(currentScope, elseDescriptor);
        currentScope.addChildScope(elseScope);
        mBody.collectData(elseScope);
    }
}