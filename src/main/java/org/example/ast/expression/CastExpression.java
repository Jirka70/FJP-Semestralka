package org.example.ast.expression;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.CastException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.TypeFactory;
import org.example.util.Location;

public class CastExpression extends UnaryExpression {
    public final String mTypeType;

    public CastExpression(AbstractExpression expression, String typeType, Location location) {
        super(expression, ExpressionType.CAST, location);
        this.mTypeType = typeType;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        AbstractType castType = TypeFactory.fromString(mTypeType);
        AbstractType type = mExpression.evaluateType(abstractScope);

        if (!castType.isCompatibleWith(type)) {
            throw new CastException("Cannot cast type " + type.mName + " to " + castType.mName);
        }

        return castType;
    }

    @Override
    public String toString() {
        return "(" + mTypeType + ")" + mExpression.toString();
    }

    @Override
    public void analyze(AbstractScope abstractScope) {

    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }
}
