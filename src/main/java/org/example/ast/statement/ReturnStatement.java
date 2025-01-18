package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.MethodDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
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

}
