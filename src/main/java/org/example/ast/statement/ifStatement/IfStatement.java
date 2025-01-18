package org.example.ast.statement.ifStatement;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.statement.AbstractStatement;
import org.example.ast.statement.StatementType;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.IfDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.BooleanType;
import org.example.util.Location;


public class IfStatement extends AbstractStatement {
    private static final String IF_KEYWORD = "if";
    public final AbstractExpression mExpression;
    public final ElseStatement mElseStatement;
    public final AbstractStatement mBody;

    public IfStatement(AbstractExpression expression, AbstractStatement body, ElseStatement elseStatement,
                       Location location) {
        super(StatementType.IF, location);
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
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractType type = mExpression.evaluateType(abstractScope);
        mExpression.analyze(abstractScope);

        System.out.println("if: " + mExpression);
        mExpression.analyze(abstractScope);

        if (!(type instanceof BooleanType)) {
            throw new TypeMismatchException("Expression in if statement is not boolean expression on " + mLocation);
        }

        AbstractSymbol ifSymbol = new StatementSymbol(IF_KEYWORD, mLocation);
        AbstractScope ifScope = abstractScope.getChildScopeBySymbol(ifSymbol);

        if (ifScope == null) {
            throw new InvalidStatementException("If statement on location " + mLocation + " was not found");
        }

        mBody.analyze(ifScope);

        if (hasElse()) {
            mElseStatement.analyze(abstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor ifDescriptor = new IfDescriptor();
        AbstractScope ifAbstractScope = new BlockScope(currentAbstractScope, ifDescriptor);
        AbstractSymbol ifSymbol = new StatementSymbol(IF_KEYWORD, mLocation);
        currentAbstractScope.addChildScope(ifSymbol, ifAbstractScope);
        mBody.collectData(ifAbstractScope);

        if (hasElse()) {
            mElseStatement.collectData(currentAbstractScope);
        }
    }
}
