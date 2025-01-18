package org.example.ast.expression;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.UndefinedMethodException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.MethodDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.ClassScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.MethodSymbol;
import org.example.semantic.type.*;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MethodCallExpression extends AbstractExpression {
    public final String mName;
    public final List<AbstractExpression> mArgs = new ArrayList<>();

    public MethodCallExpression(String name, List<AbstractExpression> args, Location location) {
        super(ExpressionType.METHOD_CALL, location);
        mName = name;
        mArgs.addAll(args);
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        List<AbstractType> argsTypes = collectArgsTypes(abstractScope);
        AbstractSymbol methodSymbol = new MethodSymbol(mName, argsTypes);
        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(methodSymbol, mLocation);

        if (descriptor instanceof MethodDescriptor methodDescriptor) {
            return TypeFactory.fromString(methodDescriptor.mReturnType);
        }

        List<MethodDescriptor> candidateMethods = findAllMatchingMethods(abstractScope);

        if (candidateMethods.isEmpty()) {
            throw new UndefinedMethodException("No suitable method " + mName + " was found for this method call on "
                    + mLocation);
        }

        MethodDescriptor mostSuitableMethod = chooseTheMostSuitableMethod(candidateMethods, argsTypes);

        return TypeFactory.fromString(mostSuitableMethod.mReturnType);
    }

    private MethodDescriptor chooseTheMostSuitableMethod(List<MethodDescriptor> descriptors,
                                                         List<AbstractType> givenArgsTypes) {
        MethodDescriptor mostSuitableMethod = null;
        int minScore = Integer.MAX_VALUE;

        for (MethodDescriptor methodDescriptor : descriptors) {
            int score = calculateMethodDescriptorScore(methodDescriptor, givenArgsTypes);
            if (score < minScore) {
                minScore = score;
                mostSuitableMethod = methodDescriptor;
            }
        }

        return mostSuitableMethod;
    }

    private int calculateMethodDescriptorScore(MethodDescriptor methodDescriptor, List<AbstractType> givenArgsTypes) {
        List<VariableDescriptor> declaredParameters = methodDescriptor.mFormalVariableDescriptors;
        List<AbstractType> declaredTypes = declaredParameters.stream()
                .map(el -> TypeFactory.fromString(el.mType))
                .toList();

        int score = 0;

        for (int i = 0; i < givenArgsTypes.size(); i++) {
            AbstractType declaredType = declaredTypes.get(i);
            AbstractType givenType = givenArgsTypes.get(i);

            if (givenType.equals(declaredType)) {
                continue;
            }

            if (declaredType instanceof FloatType) {
                if (givenType instanceof CharType) score += 2;
                if (givenType instanceof IntType) score += 1;
                continue;
            }

            if (declaredType instanceof IntType) {
                if (givenType instanceof CharType) score += 1;
                continue;
            }
        }

        return score;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(mName + "(");
        for (AbstractExpression arg : mArgs)
            result.append(arg.toString()).append(", ");
        result.append(")");
        return result.toString();
    }

    private List<AbstractType> collectArgsTypes(AbstractScope scope) throws SemanticException {
        List<AbstractType> types = new ArrayList<>();

        for (AbstractExpression expression : mArgs) {
            AbstractType expressionType = expression.evaluateType(scope);
            types.add(expressionType);
        }

        return types;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        List<AbstractType> argsTypes = collectArgsTypes(abstractScope);
        AbstractSymbol methodSymbol = new MethodSymbol(mName, argsTypes);
        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(methodSymbol, mLocation);

        if (descriptor instanceof MethodDescriptor) { // method was found
            return;
        }

        // finding all methods with compatible formal parameters
        List<MethodDescriptor> descriptors = findAllMatchingMethods(abstractScope);
        if (descriptors.isEmpty()) { // no method is defined un current scope
            throw new UndefinedMethodException("No suitable method " + mName + " was found for this method call on "
                + mLocation);
        }

        for (AbstractExpression arg : mArgs) {
            arg.analyze(abstractScope);
        }
    }

    private List<MethodDescriptor> findAllMatchingMethods(AbstractScope scope) throws SemanticException {
        List<AbstractType> argsTypes = collectArgsTypes(scope);

        AbstractScope currentScope = scope;

        List<MethodDescriptor> methodDescriptors = new ArrayList<>();

        while (currentScope.hasParent()) {
            if (currentScope instanceof ClassScope classScope) {
                List<MethodDescriptor> methodDescriptorsInCurrentScope
                        = findAllMatchingMethodDescriptorsInClassScope(argsTypes, classScope);
                methodDescriptors.addAll(methodDescriptorsInCurrentScope);
            }

            currentScope = currentScope.mParentAbstractScope;
        }

        return methodDescriptors;
    }

    private List<MethodDescriptor> findAllMatchingMethodDescriptorsInClassScope(List<AbstractType> argsTypes,
                                                                                ClassScope classScope) {
        Collection<AbstractSymbol> symbolsInClassScope = classScope.getAllSymbols();
        List<MethodDescriptor> matchingMethods = new ArrayList<>();

        for (AbstractSymbol symbol : symbolsInClassScope) {
            if (symbol instanceof MethodSymbol methodSymbol) {
                List<AbstractType> declaredTypes = methodSymbol.mParameterTypes;
                if (canBeAllTypesAssignedToDeclaredTypes(argsTypes, declaredTypes)
                    && mName.equals(methodSymbol.mName)) {
                    MethodDescriptor descriptor = (MethodDescriptor)
                            classScope.getSymbolDescriptorOnLocation(symbol, mLocation);

                    matchingMethods.add(descriptor);
                }
            }
        }

        return matchingMethods;
    }

    private boolean canBeAllTypesAssignedToDeclaredTypes(List<AbstractType> assigningTypes,
                                                         List<AbstractType> declaredTypes) {
        if (assigningTypes.size() != declaredTypes.size()) {
            return false;
        }

        for (int i = 0; i < assigningTypes.size(); i++) {
            AbstractType assigningType = assigningTypes.get(i);
            AbstractType declaredType = declaredTypes.get(i);

            if (!assigningType.canBeAssignedTo(declaredType)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }
}
