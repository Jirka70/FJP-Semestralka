package org.example.ast;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.EmptyExpression;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.Symbol;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class Variable implements ISemanticallyAnalyzable {
    private static final String FINAL_KEYWORD = "finalis";

    public final List<String> mModifiers = new ArrayList<>();
    public final String mDeclaredType;
    public final String mName;
    public final AbstractExpression mExpression;

    /**
     * Class representing variable in the source code.
     * @param modifiers - stands for modifiers of variable. If empty, variable has no modifiers
     * @param expression - RHS of assignment operator
     * */
    public Variable(List<String> modifiers, String type, String name, AbstractExpression expression) {
        if (modifiers != null) {
            mModifiers.addAll(modifiers);
        }
        mDeclaredType = type;
        mName = name;
        mExpression = expression;
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
    public void analyze(SymbolTable symbolTable) {

    }

    public boolean isAssigned() {
        return mExpression != null
                && !(mExpression instanceof EmptyExpression);
    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor variableDescriptor = new VariableDescriptor(mName, mDeclaredType, isAssigned(), isFinal());
        Symbol symbol = new Symbol(mName);
        currentScope.addSymbol(symbol, variableDescriptor);
    }
}
