package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.util.Location;

public class ParExpression extends PrimaryExpression {
    public final AbstractExpression mExpression;

    public ParExpression(AbstractExpression expression, Location location) {
        super(ExpressionType.PARENTHESES, location);
        mExpression = expression;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        return mExpression.evaluateType(abstractScope);
    }

    @Override
    public String toString() {
        return "(" + mExpression.toString() + ")";
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        mExpression.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        mExpression.generate(currentAbstractScope, generator);
    }
}
