package org.example.ast.statement;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.ForLoopDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

public class ForLoopStatement extends AbstractStatement {
    public final ForLoopControl mForLoopControl;
    public final Block mBody;

    public ForLoopStatement(ForLoopControl forLoopControl, Block body) {
        super(StatementType.FOR);
        mForLoopControl = forLoopControl;
        mBody = body;
    }

    @Override
    public String toString() {
        return mForLoopControl + "{ " + mBody + " }";
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor forLoopDescriptor = new ForLoopDescriptor();
        Scope forLoopScope = new Scope(currentScope, forLoopDescriptor);
        currentScope.addChildScope(forLoopScope);
        mForLoopControl.collectData(forLoopScope);
        mBody.collectData(forLoopScope);
    }
}
