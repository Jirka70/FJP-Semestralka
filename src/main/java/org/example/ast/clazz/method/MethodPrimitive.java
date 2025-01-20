package org.example.ast.clazz.method;

import org.example.ast.statement.AbstractBlockStatement;
import org.example.ast.statement.Block;
import org.example.ast.statement.ReturnStatement;
import org.example.codeGeneration.CodeGenerator;
import org.example.codeGeneration.IGeneratable;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.exception.symbolTableException.MethodAlreadyDefinedException;
import org.example.semantic.exception.symbolTableException.UndefinedTypeException;
import org.example.semantic.exception.symbolTableException.UnsupportedNameException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.ClassDescriptor;
import org.example.semantic.symbolTable.descriptor.MethodDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.MethodSymbol;
import org.example.semantic.symbolTable.symbol.TypeSymbol;
import org.example.semantic.type.AbstractType;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class MethodPrimitive implements ISemanticallyAnalyzable, IGeneratable {
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
                formalParametersDescriptors, getFullMethodName(classAbstractScope));


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
                        + mParameters.mParameters
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

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating method " + mName);

        List<AbstractType> parameterTypes = mParameters.collectParameterTypesFromMethod();
        AbstractSymbol methodSymbol = new MethodSymbol(mName, parameterTypes);
        AbstractScope methodAbstractScope = currentAbstractScope.getChildScopeBySymbol(methodSymbol);
        MethodDescriptor descriptor = (MethodDescriptor) methodAbstractScope.mScopeDescriptor;

        String label = descriptor.hashCode() + "_" + descriptor.mFullMethodName;
        generator.addCodeLabel(label);
        generator.newStackFrame();
        generator.mapLocalVariables(methodAbstractScope);
        generator.addInstruction("INT 0 " + generator.getStackFrameSize());
        initParameters(generator);

        mMethodBody.generate(methodAbstractScope, generator);

        generator.addInstruction("RET 0 0");
        System.out.println(generator.mCurrentStackFrameMappings);
    }

    private String getFullMethodName(AbstractScope currentAbstractScope) {
        StringBuilder fullMethodName = new StringBuilder(mName);
        AbstractScope tmpScope = currentAbstractScope;
        while (tmpScope.hasParent())  {
            ClassDescriptor classDescriptor = (ClassDescriptor) tmpScope.mScopeDescriptor;
            fullMethodName.insert(0, classDescriptor.mClassName + ".");
            tmpScope = tmpScope.mParentAbstractScope;
        }
        return fullMethodName.toString();
    }

    private void initParameters(CodeGenerator generator) {
        int paramsSize = mParameters.mParameters
                .stream()
                .mapToInt(value -> generator.typeSize(value.mDeclaredType))
                .sum();
        int argAddress = -paramsSize;

        for (ParameterPrimitive param : mParameters.mParameters) {
            int paramAddress = generator.getStackFrameAddress(param.mName);
            int paramSize = generator.typeSize(param.mDeclaredType);
            for (int i = 0; i < paramSize; i++) {
                generator.addInstruction("LOD 0 " + (argAddress + i));
            }

            for (int i = paramSize - 1; i >= 0; i--) {
                generator.addInstruction("STO 0 " + (paramAddress + i));
            }

            argAddress += paramSize;
        }
    }
}
