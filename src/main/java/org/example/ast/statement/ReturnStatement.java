package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.CastExpression;
import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.MethodDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.FloatType;
import org.example.semantic.type.TypeFactory;
import org.example.util.Location;

public class ReturnStatement extends AbstractStatement {
    public final AbstractExpression mExpression;

    public ReturnStatement(AbstractExpression expression, Location location) {
        super(StatementType.RETURN, location);
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "Return Statement: " + mExpression;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        mExpression.analyze(abstractScope);
        MethodDescriptor methodDescriptor = obtainMethodDescriptor(abstractScope);

        String declaredReturnTypeRaw = methodDescriptor.mReturnType;
        AbstractType declaredReturnType = TypeFactory.fromString(declaredReturnTypeRaw);
        AbstractType returnType = mExpression.evaluateType(abstractScope);

        if (!returnType.canBeAssignedTo(declaredReturnType)) {
            throw new TypeMismatchException("Return type " + returnType.mName + " does not match declared type "
                + declaredReturnType.mName + " on " + mLocation);
        }
    }

    private MethodDescriptor obtainMethodDescriptor(AbstractScope abstractScope) throws InvalidStatementException {
        AbstractScope currentAbstractScope = abstractScope;

        while (currentAbstractScope != null) {
            AbstractDescriptor scopeDescriptor = currentAbstractScope.mScopeDescriptor;
            if (scopeDescriptor instanceof MethodDescriptor methodDescriptor) {
                return methodDescriptor;
            }

            currentAbstractScope = currentAbstractScope.mParentAbstractScope;
        }

        throw new InvalidStatementException("Return statement cannot be outside the method on " + mLocation);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        AbstractExpression realReturnValue = mExpression;
        MethodDescriptor methodDescriptor;
        AbstractType providedType;
        try {
            methodDescriptor = obtainMethodDescriptor(currentAbstractScope);
            providedType = mExpression.evaluateType(currentAbstractScope);
        } catch (SemanticException e) {
            throw new RuntimeException(e); // should not happen in this phase
        }
        AbstractType expectedType = TypeFactory.fromString(methodDescriptor.mReturnType);

        if (expectedType instanceof FloatType) {
            if (!(providedType instanceof FloatType)) {
                realReturnValue = new CastExpression(realReturnValue, FloatType.FLOAT_KEYWORD, mLocation);
            }
        }

        realReturnValue.generate(currentAbstractScope, generator);

        int retValueSize = generator.typeSize(methodDescriptor.mReturnType);
        int paramsSize = methodDescriptor.mFormalVariableDescriptors
                .stream()
                .mapToInt(value -> generator.typeSize(value.mType))
                .sum();
        int retAddress = -paramsSize - retValueSize;
        for (int i = 0; i < retValueSize; i++) {
            generator.addInstruction("STO 0 " + (retAddress + i));
        }

        generator.addInstruction("RET 0 0");
    }
}
