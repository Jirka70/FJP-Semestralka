package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.FinalVariableOverwrittenException;
import org.example.semantic.exception.symbolTableException.UndefinedVariableException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.VariableSymbol;
import org.example.semantic.type.AbstractType;
import org.example.util.Location;

public class PrefixExpression extends UnaryExpression {
    public PrefixExpression(AbstractExpression expression, ExpressionType operand, Location location) {
        super(expression, operand, location);
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        return mExpression.evaluateType(abstractScope);
    }

    @Override
    public String toString() {
        return mExpressionType.toString() + mExpression.toString();
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        if (mExpression instanceof IdentifierExpression identifierExpression) {
            String name = identifierExpression.mIdentifier;
            VariableSymbol symbol = new VariableSymbol(name);
            AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);
            if (!(descriptor instanceof VariableDescriptor variableDescriptor)) {
                throw new UndefinedVariableException("Identifier " + name
                        + " is not variable, so it cannot be used in this expression on " + mLocation);
            }

            if (variableDescriptor.mIsFinal) {
                throw new FinalVariableOverwrittenException("Cannot overwrite variable with modifier \"finalis\" on " +
                        mLocation);
            }
        }

        mExpression.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentScope, CodeGenerator generator) {
        System.out.println("Generating prefix expression " + this);
        if (mExpressionType.equals(ExpressionType.PRE_INC)) {
            generateIncExpression(currentScope, generator);
        } else if (mExpressionType.equals(ExpressionType.PRE_DEC)) {
            generateDecExpression(currentScope, generator);
        } else if (mExpressionType.equals(ExpressionType.TILDE)) {
            mExpression.generate(currentScope, generator); // -x - 1
            generator.addInstruction("OPR 0 1");
            generator.addInstruction("LIT 0 1");
            generator.addInstruction("OPR 0 3");
        } else if (mExpressionType.equals(ExpressionType.NEG)) {
            generator.addInstruction("LIT 0 1"); // 1 - 0, 1 - 1
            mExpression.generate(currentScope, generator);
            generator.addInstruction("OPR 0 3");
        } else if (mExpressionType.equals(ExpressionType.UNARY_PLUS)) {
            mExpression.generate(currentScope, generator);
        } else if (mExpressionType.equals(ExpressionType.UNARY_MINUS)) {
            mExpression.generate(currentScope, generator);
            generator.addInstruction("OPR 0 1");
        }
    }
}