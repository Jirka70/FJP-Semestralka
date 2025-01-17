package org.example.ast.expression;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.FinalVariableOverwrittenException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.exception.symbolTableException.UndefinedIdentifierException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.VariableSymbol;
import org.example.semantic.type.*;
import org.example.util.Location;

public class BinaryExpression extends AbstractExpression {
    public final AbstractExpression mLeftExpression;
    public final AbstractExpression mRightExpression;

    public BinaryExpression(AbstractExpression leftExpression,
                            AbstractExpression rightExpression, ExpressionType operator, Location location) {
        super(operator, location);
        mLeftExpression = leftExpression;
        mRightExpression = rightExpression;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        AbstractType leftType = mLeftExpression.evaluateType(abstractScope);
        AbstractType rightType = mRightExpression.evaluateType(abstractScope);

        ExpressionType operator = mExpressionType;

        if (operator == ExpressionType.BOOL_EQUALS || operator == ExpressionType.NOT_EQUAL_TO) {
            return determineTypeForBoolEquals(leftType, rightType);
        } else if (operator.isInequalityType()) {
            return determineTypeForInequalityOperators(leftType, rightType);
        } else if (operator.isLogicalType()) {
            return determineTypeForLogicalOperator(leftType, rightType);
        }

        return leftType.combineWith(rightType);
    }

    private AbstractType determineTypeForLogicalOperator(AbstractType leftType, AbstractType rightType)
            throws SemanticException {
        if (!(leftType instanceof BooleanType) || !(rightType instanceof BooleanType)) {
            throw new TypeMismatchException("Type " + leftType.mName + " and " + rightType.mName +
                    " are not able to be used in logical operations (&&, ||) on " + mLocation);
        }

        return new BooleanType();
    }

    private AbstractType determineTypeForInequalityOperators(AbstractType leftType, AbstractType rightType)
            throws SemanticException {
        if (!(leftType instanceof NumberType) || !(rightType instanceof NumberType)) {
            throw new TypeMismatchException("Type " + leftType.mName + " and " + rightType.mName +
                    " are not able to compare in inequality on " + mLocation);
        }

        return new BooleanType();
    }

    private AbstractType determineTypeForBoolEquals(AbstractType leftType, AbstractType rightType)
            throws SemanticException {
        if (leftType instanceof PrimitiveType && rightType instanceof PrimitiveType) {
            return new BooleanType();
        } else if (leftType instanceof ObjectType && rightType instanceof ObjectType) {
            return new BooleanType();
        }

        throw new TypeMismatchException("Type " + leftType.mName + " cannot be compared with " + rightType.mName
                + " on: " + mLocation);
    }

    @Override
    public String toString() {
        return mLeftExpression.toString() + mExpressionType.toString() + mRightExpression.toString();
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        if (mLeftExpression instanceof IdentifierExpression identifierExpression) {
            AbstractSymbol symbol = new VariableSymbol(identifierExpression.mIdentifier);
            AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);

            if (!(descriptor instanceof VariableDescriptor variableDescriptor)) {
                throw new UndefinedIdentifierException("Identifier " + identifierExpression.mIdentifier + " is not" +
                        " defined in the current scope on " + mLocation);
            }

            AbstractType variableType = TypeFactory.fromString(variableDescriptor.mType);
            AbstractType rightType = mRightExpression.evaluateType(abstractScope);
            if (!rightType.canBeAssignedTo(variableType)) {
                throw new TypeMismatchException("Type " + rightType.mName + " cannot be assigned to type "
                        + variableType.mName + " on location " + mLocation);
            }

            if (variableDescriptor.mIsFinal) {
                throw new FinalVariableOverwrittenException("Finalis identifier " + identifierExpression.mIdentifier +
                        " cannot be overwritten on " + mLocation);
            }
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }
}
