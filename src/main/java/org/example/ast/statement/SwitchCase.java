package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class SwitchCase implements ISemanticallyAnalyzable {
    public final AbstractExpression mExpression;
    public final List<AbstractBlockStatement> mBody = new ArrayList<>();

    public SwitchCase(AbstractExpression expression, List<AbstractBlockStatement> body) {
        mExpression = expression;
        if (body != null)
            mBody.addAll(body);
    }

    @Override
    public String toString() {
        return "Switch Case: " + mExpression + " " + mBody;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor caseDescriptor = new CaseDescriptor();
        Scope caseScope = new Scope(currentScope, caseDescriptor);
        currentScope.addChildScope(caseScope);

        for (AbstractBlockStatement blockStatement : mBody) {
            blockStatement.collectData(caseScope);
        }
    }
}
