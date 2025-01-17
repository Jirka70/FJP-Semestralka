package org.example.ast.expression;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.UndefinedMethodException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.MethodDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.MethodSymbol;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.TypeFactory;
import org.example.util.Location;

import java.util.ArrayList;
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
        List<AbstractType> argsTypes = collectArgsTypes(abstractScope);
        AbstractSymbol symbol = new MethodSymbol(mName, argsTypes);

        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);

        if (!(descriptor instanceof MethodDescriptor methodDescriptor)) {
            throw new UndefinedMethodException("Cannot call method \""
                    + mName
                    + "\" because it is not defined with those parameter types on "
                    + mLocation);
        }

        List<VariableDescriptor> expectedArgs = methodDescriptor.mFormalVariableDescriptors;

        if (expectedArgs.size() != mArgs.size()) {
            throw new UndefinedMethodException("Method " + mName + " with "+ mArgs.size()
                    + " parameters is not defined in current scope on " + mLocation);
        }

        for (int i = 0; i < mArgs.size(); i++) {
            VariableDescriptor expectedParameter = expectedArgs.get(i);
            AbstractExpression actualParameter = mArgs.get(i);

            AbstractType expectedType = TypeFactory.fromString(expectedParameter.mType);
            AbstractType actualType = actualParameter.evaluateType(abstractScope);

            if (!actualType.canBeAssignedTo(expectedType)) {
                throw new UndefinedMethodException("Method \""
                        + mName
                        + "\" with provided parameters is not defined in current scope on "
                        + mLocation);
            }
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }
}
