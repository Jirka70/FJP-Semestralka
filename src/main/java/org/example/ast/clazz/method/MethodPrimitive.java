package org.example.ast.clazz.method;

import org.example.ast.statement.Block;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.Symbol;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.MethodDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class MethodPrimitive implements ISemanticallyAnalyzable {
    public final String mDeclaredReturnType;
    public final String mName;
    public final List<ParameterPrimitive> mParameters = new ArrayList<>();
    public final Block mMethodBody;

    public MethodPrimitive(String returnType, String name, List<ParameterPrimitive> parameters, Block methodBody) {
        mDeclaredReturnType = returnType;
        mName = name;
        if (parameters != null)
            mParameters.addAll(parameters);
        mMethodBody = methodBody;
    }

    @Override
    public String toString() {
        return mDeclaredReturnType + " " + mName + " " + mParameters + " " + mMethodBody;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor methodDescriptor = new MethodDescriptor(mName, mDeclaredReturnType);

        Scope methodScope = new Scope(currentScope, methodDescriptor);
        currentScope.addChildScope(methodScope);
        Symbol methodSymbol = new Symbol(mName);
        currentScope.addSymbol(methodSymbol, methodDescriptor);

        for (ParameterPrimitive parameter : mParameters) {
            parameter.collectData(methodScope);
        }

        mMethodBody.collectData(methodScope);
    }
}
