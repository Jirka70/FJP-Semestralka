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

public class PostfixExpression extends UnaryExpression {
    public PostfixExpression(AbstractExpression expression, ExpressionType operand, Location location) {
        super(expression, operand, location);
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        return mExpression.evaluateType(abstractScope);
    }

    @Override
    public String toString() {
        return mExpression.toString() + mExpressionType.toString();
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
                throw new FinalVariableOverwrittenException("Cannot overwrite variable with modifier \"finalis\" on "
                        + mLocation);
            }
        }

        mExpression.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }
    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating postfix expression " + this);
        mExpression.generate(currentAbstractScope, generator);
        if (mExpressionType.equals(ExpressionType.POST_INC)) {
            generateIncExpression(currentAbstractScope, generator);
            generator.addInstruction("INT 0 " + -expressionTypeSize(currentAbstractScope, generator)); // ignore updated value
        } else if (mExpressionType.equals(ExpressionType.POST_DEC)) {
            generateDecExpression(currentAbstractScope, generator);
            generator.addInstruction("INT 0 " + -expressionTypeSize(currentAbstractScope, generator)); // ignore updated value
        }
    }
}