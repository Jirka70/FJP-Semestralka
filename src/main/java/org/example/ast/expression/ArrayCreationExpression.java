package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.ObjectType;
import org.example.util.Location;

import java.util.List;

public class ArrayCreationExpression extends AbstractExpression {
    public final String mCreatedName;
    public final AbstractExpression mSizeExpression;
    public final List<AbstractExpression> mInitializer;

    public ArrayCreationExpression(String createdName, AbstractExpression sizeExpression, Location location) {
        super(ExpressionType.ARRAY_CREATION, location);
        mCreatedName = createdName;
        if (sizeExpression == null)
            throw new RuntimeException("Size expression cannot be null");
        mSizeExpression = sizeExpression;
        mInitializer = null;
    }
    public ArrayCreationExpression(String createdName, List<AbstractExpression> initializer, Location location) {
        super(ExpressionType.ARRAY_CREATION, location);
        mCreatedName = createdName;
        mSizeExpression = null;
        if (initializer == null)
            throw new RuntimeException("Array initializer cannot be null");
        mInitializer = initializer;
    }

    public boolean hasSizeExpression() {
        return mSizeExpression != null;
    }

    public boolean hasInitializer() {
        return mInitializer != null;
    }

    @Override
    public String toString() {
        return "Array creation " + mCreatedName + " " + (hasSizeExpression() ? mSizeExpression : mInitializer);
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        return new ObjectType(mCreatedName + "[]");
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {

    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        if (hasSizeExpression()) {
            // size = baseTypeSize * elements + 1(for length)
            int typeSize = generator.typeSize(mCreatedName);
            LiteralExpression sizeExpr = new LiteralExpression("" + typeSize, LiteralExpression.LiteralType.DECIMAL_LITERAL, mLocation);
            BinaryExpression size = new BinaryExpression(sizeExpr, mSizeExpression, ExpressionType.MULTIPLY, mLocation);

            size.generate(currentAbstractScope, generator);
            generator.addInstruction("LIT 0 1");
            generator.addInstruction("OPR 0 2"); // add 1 for length
            generator.addInstruction("NEW 0 0");

            size.generate(currentAbstractScope, generator); // save size
            generator.addInstruction("STA 0 0");
            generator.addInstruction("INT 0 1");
        } else if (mInitializer != null) {
            // size = baseTypeSize * elements + 1(for length)
            int size = generator.typeSize(mCreatedName) * mInitializer.size() + 1;
            generator.addInstruction("LIT 0 0");
            generator.addInstruction("LIT 0 " + size);
            generator.addInstruction("NEW 0 0");
            generator.addInstruction("OPR 0 2");
            generator.addInstruction("INT 0 1");
            generator.addInstruction("LIT 0 " + size);
            generator.addInstruction("STA 0 0");

            // for size = 1
            for (AbstractExpression expr : mInitializer) {
                generator.addInstruction("INT 0 1");
                generator.addInstruction("LIT 0 1");
                generator.addInstruction("OPR 0 2");

                expr.generate(currentAbstractScope, generator);
                generator.addInstruction("STA 0 0");
            }
        }
    }
}
