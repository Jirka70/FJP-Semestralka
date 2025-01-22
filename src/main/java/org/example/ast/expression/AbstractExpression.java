package org.example.ast.expression;

import org.example.codeGeneration.IGeneratable;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.util.Location;

public abstract class AbstractExpression implements ISemanticallyAnalyzable, IGeneratable {
    public final ExpressionType mExpressionType;
    public final Location mLocation;

    protected AbstractExpression(ExpressionType expressionType, Location location) {
        mExpressionType = expressionType;
        mLocation = location;
    }

    public abstract AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException;

    @Override
    public abstract String toString();

}
