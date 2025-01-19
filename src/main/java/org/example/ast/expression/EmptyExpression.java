package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.util.Location;

public class EmptyExpression extends AbstractExpression {

    public EmptyExpression(Location location) {
        super(ExpressionType.EMPTY, location);
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        return null;
    }

    @Override
    public String toString() {
        return "EmptyExpression";
    }

    @Override
    public void analyze(AbstractScope abstractScope) {

    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        // empty
    }
}
