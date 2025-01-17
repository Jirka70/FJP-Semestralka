package org.example.ast;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.EmptyExpression;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.UnknownModifierException;
import org.example.semantic.exception.symbolTableException.UnsupportedNameException;
import org.example.semantic.exception.symbolTableException.VariableAlreadyDefinedException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.VariableSymbol;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class Variable implements ISemanticallyAnalyzable {
    private static final String FINAL_KEYWORD = "finalis";

    public final List<String> mModifiers = new ArrayList<>();
    public final String mDeclaredType;
    public final String mName;
    public final AbstractExpression mExpression;
    public final Location mLocation;

    /**
     * Class representing variable in the source code.
     * @param modifiers - stands for modifiers of variable. If empty, variable has no modifiers
     * @param expression - RHS of assignment operator
     * */
    public Variable(List<String> modifiers, String type, String name, AbstractExpression expression, Location location) {
        if (modifiers != null) {
            mModifiers.addAll(modifiers);
        }
        mDeclaredType = type;
        mName = name;
        mExpression = expression;
        mLocation = location;
    }

    public boolean isFinal() {
        return mModifiers.contains(FINAL_KEYWORD);
    }

    public boolean hasModifier() {
        return mModifiers.isEmpty();
    }

    @Override
    public String toString() {
        return  mModifiers + " " + mDeclaredType + " " + mName + (mExpression != null ? " = " + mExpression : "");
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        for (String modifier : mModifiers) {
            if (!modifier.equals(FINAL_KEYWORD)) {
                throw new UnknownModifierException("Modifier " + modifier + " is not defined on " + mLocation);
            }
        }

        if (mExpression != null) {
            mExpression.analyze(abstractScope);
        }
    }

    public boolean isAssigned() {
        return mExpression != null
                && !(mExpression instanceof EmptyExpression);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractSymbol abstractSymbol = new VariableSymbol(mName);

        if (currentAbstractScope.isDefinedAsType(abstractSymbol)) {
            throw new UnsupportedNameException("Variable name " + mName + " cannot be used as name, because it is a type");
        }

        if (currentAbstractScope.containsSymbolInScopeOnLocation(abstractSymbol, mLocation)) {
            throw new VariableAlreadyDefinedException("Variable " + mName + " is already defined in the scope on "
                    + mLocation);
        }

        AbstractDescriptor variableDescriptor = new VariableDescriptor(mName, mDeclaredType, isAssigned(), isFinal());
        AbstractSymbol symbol = new VariableSymbol(mName);
        currentAbstractScope.addSymbol(symbol, variableDescriptor, mLocation);
    }
}
