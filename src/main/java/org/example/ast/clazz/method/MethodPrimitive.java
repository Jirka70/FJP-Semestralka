package org.example.ast.clazz.method;

import org.example.ast.statement.AbstractBlockStatement;
import org.example.ast.statement.Block;
import org.example.ast.statement.ReturnStatement;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.exception.symbolTableException.MethodAlreadyDefinedException;
import org.example.semantic.exception.symbolTableException.UndefinedTypeException;
import org.example.semantic.exception.symbolTableException.UnsupportedNameException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.MethodDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.MethodSymbol;
import org.example.semantic.symbolTable.symbol.TypeSymbol;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class MethodPrimitive implements ISemanticallyAnalyzable {
    private static final String VOID_KEYWORD = "vacuum";
    public final String mDeclaredReturnType;
    public final String mName;
    public final FormalParameters mParameters;
    public final Block mMethodBody;
    public final Location mLocation;

    public MethodPrimitive(String returnType, String name, FormalParameters parameters, Block methodBody,
                           Location location) {
        mDeclaredReturnType = returnType;
        mName = name;
        mParameters = parameters;
        mMethodBody = methodBody;
        mLocation = location;
    }

    @Override
    public String toString() {
        return mDeclaredReturnType + " " + mName + " " + mParameters + " " + mMethodBody;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        TypeSymbol returnSymbol = new TypeSymbol(mDeclaredReturnType);
        if (!abstractScope.isTypeDefined(returnSymbol)) {
            throw new UndefinedTypeException("Return type of method " + mName + " is not defined in current scope on "
                    + mLocation);
        }

        List<ParameterPrimitive> parameterPrimitives = mParameters.mParameters;
        for (ParameterPrimitive parameterPrimitive : parameterPrimitives) {
            parameterPrimitive.analyze(abstractScope);
        }

        if (isNonVoid()) {
            AbstractBlockStatement lastStatement = getLastMethodStatement();
            if (!(lastStatement instanceof ReturnStatement)) {
                throw new InvalidStatementException("In non vacuum method, there must be specified return statement "
                    + "as last statement");
            }
        }

        if (mMethodBody != null) {
            mMethodBody.analyze(abstractScope);
        }


    }

    private AbstractBlockStatement getLastMethodStatement() {
        int size = mMethodBody.mBlockStatements.size();
        return mMethodBody.mBlockStatements.get(size - 1);
    }

    public boolean isNonVoid() {
        return !mDeclaredReturnType.equals(VOID_KEYWORD);
    }

    @Override
    public void collectData(AbstractScope classAbstractScope) throws SemanticException {
        List<VariableDescriptor> formalParametersDescriptors = createFormalParametersDescriptors();
        AbstractDescriptor methodDescriptor = new MethodDescriptor(mName, mDeclaredReturnType,
                formalParametersDescriptors);


        AbstractScope methodScope = new BlockScope(classAbstractScope, methodDescriptor);
        AbstractSymbol methodSymbol = new MethodSymbol(mName, mParameters.collectParameterTypesFromMethod());

        if (classAbstractScope.isTypeDefined(methodSymbol)) {
            throw new UnsupportedNameException("Method name " + mName + " cannot be used as name, because it is a type");
        }

        classAbstractScope.addChildScope(methodSymbol, methodScope);

        if (classAbstractScope.isSymbolDefinedOnLocation(methodSymbol, mLocation)) {
            AbstractDescriptor sameNameMethodDescriptor = classAbstractScope.getSymbolDescriptorOnLocation(methodSymbol,
                    mLocation);
            if (sameNameMethodDescriptor.equals(methodDescriptor)) {
                throw new MethodAlreadyDefinedException("Method with name '"
                        + mName
                        + "' and with parameters '"
                        + mParameters
                        + "' already exists");
            }
        }

        classAbstractScope.addSymbol(methodSymbol, methodDescriptor, mLocation);

        List<ParameterPrimitive> parameterPrimitives = mParameters.mParameters;
        for (ParameterPrimitive parameter : parameterPrimitives) {
            parameter.collectData(methodScope);
        }

        if (mMethodBody != null) {
            mMethodBody.collectData(methodScope);
        }
    }

    private List<VariableDescriptor> createFormalParametersDescriptors() {
        List<ParameterPrimitive> parameterPrimitives = mParameters.mParameters;
        List<VariableDescriptor> formalParametersDescriptors = new ArrayList<>();

        for (ParameterPrimitive parameter : parameterPrimitives) {
            VariableDescriptor formalParameterDescriptor = createFormalParameterDescriptor(parameter);
            formalParametersDescriptors.add(formalParameterDescriptor);
        }

        return formalParametersDescriptors;
    }

    private VariableDescriptor createFormalParameterDescriptor(ParameterPrimitive parameter) {
        return new VariableDescriptor(parameter.mName,
                parameter.mDeclaredType,
                true,
                parameter.isFinal());
    }
}
