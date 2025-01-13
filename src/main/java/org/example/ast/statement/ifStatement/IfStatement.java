package org.example.ast.statement.ifStatement;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.statement.AbstractStatement;
import org.example.ast.statement.StatementType;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.IfDescriptor;
import org.example.semantic.symbolTable.scope.Scope;


public class IfStatement extends AbstractStatement {
    public final AbstractExpression mExpression;
    public final ElseStatement mElseStatement;
    public final AbstractStatement mBody;

    public IfStatement(AbstractExpression expression, AbstractStatement body, ElseStatement elseStatement) {
        super(StatementType.IF);
        mExpression = expression;
        mElseStatement = elseStatement;
        mBody = body;
    }

    public boolean hasElse() {
        return mElseStatement != null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("if (")
                .append(mExpression)
                .append(") {")
                .append(mBody)
                .append("}");
        if (hasElse()) {
            builder.append(" ").append(mElseStatement);
        }

        return builder.toString();
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor ifDescriptor = new IfDescriptor();
        Scope ifScope = new Scope(currentScope, ifDescriptor);
        currentScope.addChildScope(ifScope);
        mBody.collectData(ifScope);

        if (hasElse()) {
            System.out.println("has else");
            mElseStatement.collectData(currentScope);
        }
    }
}
