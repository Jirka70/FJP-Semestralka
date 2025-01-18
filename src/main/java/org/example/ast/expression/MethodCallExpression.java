package org.example.ast.expression;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.UndefinedMethodException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.MethodDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.ClassScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.MethodSymbol;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.TypeFactory;
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
        List<AbstractType> types = collectArgsTypes(abstractScope);
        AbstractSymbol symbol = new MethodSymbol(mName, types);

        List<MethodDescriptor> matchingMethods = findAllMatchingMethods(abstractScope);

        if (matchingMethods.size() == 1) {
            MethodDescriptor methodDescriptor = matchingMethods.get(0);
            return TypeFactory.fromString(methodDescriptor.mReturnType);
        }

        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);

        if (!(descriptor instanceof MethodDescriptor methodDescriptor)) {
            throw new UndefinedMethodException("Cannot call method \""
                    + mName
                    + "\" because it is not a method on "
                    + mLocation);
        }

        return TypeFactory.fromString(methodDescriptor.mReturnType);
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
        /*List<AbstractType> argsTypes = collectArgsTypes(abstractScope);
        AbstractSymbol symbol = new MethodSymbol(mName, argsTypes);

        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);

        List<MethodDescriptor> matchingMethods = findAllMatchingMethods(abstractScope);*/
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
