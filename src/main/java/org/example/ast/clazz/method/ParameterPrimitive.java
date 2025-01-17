package org.example.ast.clazz.method;

import org.example.ast.Variable;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.VariableSymbol;
import org.example.util.Location;

import java.util.List;

public class ParameterPrimitive extends Variable {

    public ParameterPrimitive(List<String> modifiers, String type, String name, Location location) {
        super(modifiers, type, name, null, location);
    }


    @Override
    public void collectData(AbstractScope currentAbstractScope) {
        VariableDescriptor variableDescriptor = new VariableDescriptor(mName, mDeclaredType, true, isFinal());
        AbstractSymbol parameterSymbol = new VariableSymbol(mName);
        currentAbstractScope.addSymbol(parameterSymbol, variableDescriptor, mLocation);
    }
}
