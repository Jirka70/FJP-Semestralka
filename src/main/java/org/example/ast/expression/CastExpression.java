package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.CastException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.*;
import org.example.util.Location;

public class CastExpression extends UnaryExpression {
    public final String mTypeType;

    public CastExpression(AbstractExpression expression, String typeType, Location location) {
        super(expression, ExpressionType.CAST, location);
        this.mTypeType = typeType;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        return TypeFactory.fromString(mTypeType);
    }

    @Override
    public String toString() {
        return "(" + mTypeType + ")" + mExpression.toString();
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractType castType = TypeFactory.fromString(mTypeType);
        AbstractType type = mExpression.evaluateType(abstractScope);

        if (!castType.isCompatibleWith(type)) {
            throw new CastException("Cannot cast type " + type.mName + " to " + castType.mName + " on " + mLocation);
        }

        mExpression.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating cast " + this);
        mExpression.generate(currentAbstractScope, generator);

        AbstractType sourceType;
        AbstractType targetType = TypeFactory.fromString(mTypeType);
        try {
            sourceType = mExpression.evaluateType(currentAbstractScope);
        } catch (SemanticException e) {
            throw new RuntimeException(e); // should not happen in this phase
        }

        // char, int -> float
        if ((sourceType instanceof CharType || sourceType instanceof IntType) && targetType instanceof FloatType) {
            generator.addInstruction("LIT 0 0");
            generator.addInstruction("ITR 0 0");
        }

        // float -> int, char
        else if (sourceType instanceof FloatType && (targetType instanceof CharType || targetType instanceof IntType)) {
            generator.addInstruction("RTI 0 1");
        }
    }
}
