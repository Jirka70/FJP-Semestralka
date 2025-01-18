package org.example.ast;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.TypeFactory;
import org.example.util.Location;

import java.util.List;

public class LocalVariable extends Variable {

    public LocalVariable(List<String> modifiers, String type, String name, AbstractExpression expression,
                         Location location) {
        super(modifiers, type, name, expression, location);
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractType expressionType = mExpression.evaluateType(abstractScope);
        AbstractType declaredType = TypeFactory.fromString(mDeclaredType);

        mExpression.analyze(abstractScope);

        if (!expressionType.canBeAssignedTo(declaredType)) {
            throw new TypeMismatchException("Cannot assign type "
                    + expressionType.mName + " to type " + mDeclaredType + " on " + mLocation);
        }
    }
}
