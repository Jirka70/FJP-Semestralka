package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class SwitchStatement extends AbstractStatement {
    public final AbstractExpression mExpression;
    public final List<SwitchCase> mCases = new ArrayList<>();

    public SwitchStatement(AbstractExpression expression, List<SwitchCase> cases) {
        super(StatementType.SWITCH);
        mExpression = expression;
        if (cases == null || cases.isEmpty())
            throw new IllegalArgumentException("Switch statement must have at least one switch case");
        mCases.addAll(cases);
    }

    @Override
    public String toString() {
        return "Switch Statement: " + mExpression + " " + mCases;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor switchDescriptor = new SwitchDescriptor();
        Scope switchScope = new Scope(currentScope, switchDescriptor);
        currentScope.addChildScope(switchScope);
        for (SwitchCase cases : mCases) {
            cases.collectData(switchScope);
        }
    }
}
