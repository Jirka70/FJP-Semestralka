package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.UndefinedIdentifierException;
import org.example.semantic.exception.symbolTableException.UndefinedVariableException;
import org.example.semantic.exception.symbolTableException.VariableNotAssignedException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.VariableSymbol;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.NullType;
import org.example.semantic.type.TypeFactory;
import org.example.util.Location;

public class IdentifierExpression extends PrimaryExpression {
    private static final String NULL_KEYWORD = "nullus";
    public final String mIdentifier;

    public IdentifierExpression(String identifier, Location location) {
        super(ExpressionType.IDENTIFIER, location);
        mIdentifier = identifier;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        if (mIdentifier.equals(NULL_KEYWORD)) {
            return new NullType();
        }

        AbstractSymbol identifierSymbol = new VariableSymbol(mIdentifier);
        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(identifierSymbol, mLocation);

        if (!(descriptor instanceof VariableDescriptor variableDescriptor)) {
            throw new UndefinedIdentifierException("Identifier '" + mIdentifier + "' is not a variable on " + mLocation);
        }

        return TypeFactory.fromString(variableDescriptor.mType);
    }

    @Override
    public String toString() {
        return mIdentifier;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractSymbol symbol = new VariableSymbol(mIdentifier);

        if (!abstractScope.isSymbolDefinedOnLocation(symbol, mLocation)) {
            throw new UndefinedIdentifierException("Identifier '" + mIdentifier + "' is not defined on " + mLocation);
        }

        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);
        if (!(descriptor instanceof VariableDescriptor variableDescriptor)) {
            throw new UndefinedVariableException("Symbol " + symbol.mName + " is not a variable on " + mLocation);
        }

        if (!variableDescriptor.mIsAssigned) {
            throw new VariableNotAssignedException("Variable '" + mIdentifier
                    + "' is not assigned and cannot be used in expression on " + mLocation);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        AbstractSymbol symbol = new VariableSymbol(mIdentifier);
        AbstractDescriptor descriptor = currentAbstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);
        VariableDescriptor varDescriptor = (VariableDescriptor) descriptor;

        int variableAddress = generator.getStackFrameAddress(varDescriptor.mName);
        int variableSize = generator.typeSize(varDescriptor.mType);
        for (int i = variableSize - 1; i >= 0; i--) {
            generator.addInstruction("LOD 0 " + (variableAddress + i));
        }
    }
}
