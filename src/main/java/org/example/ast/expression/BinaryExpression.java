package org.example.ast.expression;

import org.antlr.v4.runtime.misc.Pair;
import org.example.codeGeneration.CodeGenerator;
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
            return new BooleanType();
        } else if (operator.isInequalityType()) {
            return new BooleanType();
        } else if (operator.isLogicalType()) {
            return new BooleanType();
        }

        return leftType.combineWith(rightType);
    }

    private void validateLogicalOperator(AbstractType leftType, AbstractType rightType)
            throws SemanticException {
        if (!(leftType instanceof BooleanType) || !(rightType instanceof BooleanType)) {
            throw new TypeMismatchException("Type " + leftType.mName + " and " + rightType.mName +
                    " are not able to be used in logical operations (&&, ||) on " + mLocation);
        }
    }

    private void validateInequalityOperator(AbstractType leftType, AbstractType rightType)
            throws SemanticException {
        if (!(leftType instanceof NumberType) || !(rightType instanceof NumberType)) {
            throw new TypeMismatchException("Type " + leftType.mName + " and " + rightType.mName +
                    " are not able to compare in inequality on " + mLocation);
        }
    }

    private void validateBoolEquals(AbstractType leftType, AbstractType rightType)
            throws SemanticException {
        if (leftType instanceof PrimitiveType && rightType instanceof PrimitiveType) {
            return;
        } else if (leftType instanceof ObjectType && rightType instanceof ObjectType) {
            return;
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
        AbstractType leftType = mLeftExpression.evaluateType(abstractScope);
        AbstractType rightType = mRightExpression.evaluateType(abstractScope);

        if (!leftType.isCompatibleWith(rightType)) {
            throw new TypeMismatchException("Type " + leftType.mName + " is not compatible with " + rightType.mName +
                    " on location " + mLocation);
        }

        ExpressionType operator = mExpressionType;

        if (operator == ExpressionType.BOOL_EQUALS || operator == ExpressionType.NOT_EQUAL_TO) {
            validateBoolEquals(leftType, rightType);
        } else if (operator.isInequalityType()) {
            validateInequalityOperator(leftType, rightType);
        } else if (operator.isLogicalType()) {
            validateLogicalOperator(leftType, rightType);
        }

        if (mLeftExpression instanceof IdentifierExpression identifierExpression) {
            validateIdentifierExpression(abstractScope, identifierExpression);
        }

        mLeftExpression.analyze(abstractScope);
        mRightExpression.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        if (mExpressionType.equals(ExpressionType.ASSIGN)) {
            generateAssign(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.PLUS)) {
            generatePlus(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.MINUS)) {
            generateMinus(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.MULTIPLY)) {
            generateMultiply(currentAbstractScope, generator);
        }  else if (mExpressionType.equals(ExpressionType.DIVISION)) {
            generateDivision(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.MODULO)) {
            generateModulo(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.BOOL_EQUALS)) {
            generateEquals(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.LESS_THAN)) {
            generateLT(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.GREATER_THAN)) {
            generateGT(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.LESS_THAN_OR_EQUAL)) {
            generateLE(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.GREATER_THAN_OR_EQUAL)) {
            generateGE(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.AND)) {
            generateAnd(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.OR)) {
            generateOr(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.NOT_EQUAL_TO)) {
            generateNotEquals(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.PLUS_EQUALS)) {
            generatePlusEquals(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.MINUS_EQUALS)) {
            generateMinusEquals(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.MULTIPLY_EQUALS)) {
            generateMultiplyEquals(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.DIVIDE_EQUALS)) {
            generateDivideEquals(currentAbstractScope, generator);
        } else if (mExpressionType.equals(ExpressionType.MODULO_EQUALS)) {
            generateModuloEquals(currentAbstractScope, generator);
        }

        else throw new RuntimeException("Generate not implemented for " + this);
    }


    private void generateAssign(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating variable assignment " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        if (operands.a instanceof ArrayExpression) {
            ((ArrayExpression) operands.a).mIdentifier.generate(currentAbstractScope, generator);
            generator.addInstruction("LIT 0 1"); // skip length
            generator.addInstruction("OPR 0 2");
            ((ArrayExpression) operands.a).mIndex.generate(currentAbstractScope, generator);
            generator.addInstruction("OPR 0 2");

            operands.b.generate(currentAbstractScope, generator);
            generator.addInstruction("STA 0 0");
            return;
        }

        operands.b.generate(currentAbstractScope, generator);

        String varName = ((IdentifierExpression) operands.a).mIdentifier;
        AbstractSymbol symbol = new VariableSymbol(varName);
        AbstractDescriptor descriptor = currentAbstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);
        VariableDescriptor varDescriptor = (VariableDescriptor) descriptor;

        int variableAddress = generator.getStackFrameAddress(varDescriptor.mName);
        int variableSize = generator.typeSize(varDescriptor.mType);
        for (int i = variableSize - 1; i >= 0; i--) {
            generator.addInstruction("STO 0 " + (variableAddress + i));
        }

        generator.addInstruction("INT 0 " + variableSize); // assignment should return assigned value
    }


    // Implicit cast
    private Pair<AbstractExpression, AbstractExpression> getOperands(AbstractScope currentAbstractScope) {
        AbstractExpression leftOperand = mLeftExpression;
        AbstractExpression rightOperand = mRightExpression;
        try {
            if (mLeftExpression.evaluateType(currentAbstractScope) instanceof FloatType) {
                if (!(mRightExpression.evaluateType(currentAbstractScope) instanceof FloatType)) {
                    rightOperand = new CastExpression(mRightExpression, FloatType.FLOAT_KEYWORD, mLocation);
                }
            }
            if (mRightExpression.evaluateType(currentAbstractScope) instanceof FloatType) {
                if (!(mLeftExpression.evaluateType(currentAbstractScope) instanceof FloatType)) {
                    leftOperand = new CastExpression(mLeftExpression, FloatType.FLOAT_KEYWORD, mLocation);
                }
            }
        } catch (SemanticException e) {
            throw new RuntimeException(e); // should not happen in this phase
        }
        return new Pair<>(leftOperand, rightOperand);
    }

    private void generatePlus(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating plus " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.b.generate(currentAbstractScope, generator);
        operands.a.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 2");
        } else {
            generator.addInstruction("OPR 0 2");
        }
    }

    private boolean isRealArithmetic(AbstractScope currentAbstractScope) {
        try {
            return mLeftExpression.evaluateType(currentAbstractScope) instanceof FloatType
                    || mRightExpression.evaluateType(currentAbstractScope) instanceof FloatType;
        } catch (SemanticException e) {
            throw new RuntimeException(e); // should not happen in this phase
        }
    }

    private void generateMinus(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating minus " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 3");
        } else {
            generator.addInstruction("OPR 0 3");
        }
    }

    private void generateMultiply(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating multiply " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 4");
        } else {
            generator.addInstruction("OPR 0 4");
        }
    }

    private void generateDivision(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating division " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 5");
        } else {
            generator.addInstruction("OPR 0 5");
        }
    }

    private void generateModulo(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating modulo " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 6");
        } else {
            generator.addInstruction("OPR 0 6");
        }
    }

    private void generateEquals(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating equals " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 8");
        } else {
            generator.addInstruction("OPR 0 8");
        }
    }

    private void generateLT(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating LT " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 10");
        } else {
            generator.addInstruction("OPR 0 10");
        }
    }

    private void generateGT(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating GT " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 12");
        } else {
            generator.addInstruction("OPR 0 12");
        }
    }

    private void generateLE(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating LE " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 13");
        } else {
            generator.addInstruction("OPR 0 13");
        }
    }

    private void generateGE(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating GE " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 11");
        } else {
            generator.addInstruction("OPR 0 11");
        }
    }

    private void generateAnd(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating AND " + this);

        mLeftExpression.generate(currentAbstractScope, generator);
        mRightExpression.generate(currentAbstractScope, generator);
        generator.addInstruction("OPR 0 4"); // multiplication
    }

    private void generateOr(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating OR " + this);

        mLeftExpression.generate(currentAbstractScope, generator);
        mRightExpression.generate(currentAbstractScope, generator);
        generator.addInstruction("OPR 0 2"); // sum and > 0 check
        generator.addInstruction("LIT 0 0");
        generator.addInstruction("OPR 0 12");
    }

    private void generateNotEquals(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating not equals " + this);
        Pair<AbstractExpression, AbstractExpression> operands = getOperands(currentAbstractScope);

        operands.a.generate(currentAbstractScope, generator);
        operands.b.generate(currentAbstractScope, generator);
        if (isRealArithmetic(currentAbstractScope)) {
            generator.addInstruction("OPF 0 9");
        } else {
            generator.addInstruction("OPR 0 9");
        }
    }

    private void generatePlusEquals(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating plus equals " + this);
        generateCompoundAssign(currentAbstractScope, generator, ExpressionType.PLUS);
    }

    private void generateCompoundAssign(AbstractScope currentAbstractScope, CodeGenerator generator, ExpressionType type) {
        BinaryExpression valExpr = new BinaryExpression(mLeftExpression, mRightExpression, type, mLocation);
        BinaryExpression assignExpr = new BinaryExpression(mLeftExpression, valExpr, ExpressionType.ASSIGN, mLocation);
        assignExpr.generate(currentAbstractScope, generator);
    }

    private void generateMinusEquals(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating minus equals " + this);
        generateCompoundAssign(currentAbstractScope, generator, ExpressionType.MINUS);
    }

    private void generateMultiplyEquals(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating multiply equals " + this);
        generateCompoundAssign(currentAbstractScope, generator, ExpressionType.MULTIPLY);
    }

    private void generateDivideEquals(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating divide equals " + this);
        generateCompoundAssign(currentAbstractScope, generator, ExpressionType.DIVISION);
    }

    private void generateModuloEquals(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating modulo equals " + this);
        generateCompoundAssign(currentAbstractScope, generator, ExpressionType.MODULO);
    }

    private void validateIdentifierExpression(AbstractScope abstractScope, IdentifierExpression identifierExpression)
            throws SemanticException {


        AbstractSymbol symbol = new VariableSymbol(identifierExpression.mIdentifier);
        AbstractDescriptor descriptor = abstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);

        if (!(descriptor instanceof VariableDescriptor variableDescriptor)) {
            throw new UndefinedIdentifierException("Identifier " + identifierExpression.mIdentifier + " is not" +
                    " defined in the current scope on " + mLocation);
        }

        AbstractType variableType = TypeFactory.fromString(variableDescriptor.mType);
        AbstractType rightType = mRightExpression.evaluateType(abstractScope);

        if (mExpressionType.isAssignmentType()) {
            if (!rightType.canBeAssignedTo(variableType)) {
                throw new TypeMismatchException("Type " + rightType.mName + " cannot be used with type "
                        + variableType.mName + " on location " + mLocation);
            }
        }

        if (!rightType.isCompatibleWith(variableType)) {
            throw new TypeMismatchException("Type " + rightType.mName + " is not compatible with "
                    + variableType.mName + " on location " + mLocation);
        }

        if (variableDescriptor.mIsFinal) {
            if (mExpressionType.isAssignmentType()) {
                throw new FinalVariableOverwrittenException("Finalis identifier " + identifierExpression.mIdentifier +
                        " cannot be overwritten on " + mLocation);
            }
        }
    }
}
