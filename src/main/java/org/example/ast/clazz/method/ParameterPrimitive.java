package org.example.ast.clazz.method;

import org.example.ast.Variable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.UndefinedTypeException;
import org.example.semantic.exception.symbolTableException.UnknownModifierException;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.TypeSymbol;
import org.example.semantic.symbolTable.symbol.VariableSymbol;
import org.example.util.Location;

import java.util.List;

public class ParameterPrimitive extends Variable {

    public ParameterPrimitive(List<String> modifiers, String type, String name, Location location) {
        super(modifiers, type, name, null, location);
    }


    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        TypeSymbol typeSymbol = new TypeSymbol(mDeclaredType);
        if (!currentAbstractScope.isTypeDefined(typeSymbol)) {
            throw new UndefinedTypeException("Type " + mDeclaredType + " is not defined in current scope on " + mLocation);
        }

        for (String modifier : mModifiers) {
            if (!modifier.equals(FINAL_KEYWORD)) {
                throw new UnknownModifierException("Modifier " + modifier + " is not defined on " + mLocation);
            }
        }

        VariableDescriptor variableDescriptor = new VariableDescriptor(mName, mDeclaredType, true, isFinal());
        AbstractSymbol parameterSymbol = new VariableSymbol(mName);
        currentAbstractScope.addSymbol(parameterSymbol, variableDescriptor, mLocation);
    }
}
