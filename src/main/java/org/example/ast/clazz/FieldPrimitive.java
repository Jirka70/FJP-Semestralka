package org.example.ast.clazz;

import org.example.ast.Variable;
import org.example.ast.expression.AbstractExpression;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.exception.symbolTableException.UndefinedVariableException;
import org.example.semantic.exception.symbolTableException.VariableNotAssignedException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.VariableSymbol;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.TypeFactory;
import org.example.util.Location;

import java.util.List;

public class FieldPrimitive extends Variable {

    public FieldPrimitive(List<String> modifiers, String type, String name, AbstractExpression expression,
                          Location location) {
        super(modifiers, type, name, expression, location);
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        super.analyze(abstractScope);
        AbstractSymbol symbol = new VariableSymbol(mName);

        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);
        if (!(descriptor instanceof VariableDescriptor)) {
            throw new UndefinedVariableException("Variable " + mName + " is not variable on " + mLocation);
        }

        if (isFinal() && !isAssigned()) {
            throw new VariableNotAssignedException("Variable cannot be unassigned and be marked as finalis");
        }

        if (!isAssigned()) {
            return;
        }

        AbstractType type = mExpression.evaluateType(abstractScope);
        AbstractType expectedType = TypeFactory.fromString(mDeclaredType);

        if (!type.canBeAssignedTo(expectedType)) {
            throw new TypeMismatchException("Type \""
                    + type.mName
                    + "\" cannot be assigned to \""
                    + expectedType.mName
                    + "\" on " + mLocation);
        }
    }
}
