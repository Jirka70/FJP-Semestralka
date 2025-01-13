package org.example.ast.clazz.method;

import org.example.ast.Variable;
import org.example.semantic.symbolTable.Symbol;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.List;

public class ParameterPrimitive extends Variable {

    public ParameterPrimitive(List<String> modifiers, String type, String name) {
        super(modifiers, type, name, null);
    }


    @Override
    public void collectData(Scope currentScope) {
        VariableDescriptor variableDescriptor = new VariableDescriptor(mName, mDeclaredType, true, isFinal());
        Symbol parameterSymbol = new Symbol(mName);
        currentScope.addSymbol(parameterSymbol, variableDescriptor);
    }
}
