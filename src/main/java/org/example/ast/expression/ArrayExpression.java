package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.TypeFactory;
import org.example.util.Location;

public class ArrayExpression extends AbstractExpression {
    public final AbstractExpression mIdentifier;
    public final AbstractExpression mIndex;

    public ArrayExpression(AbstractExpression identifier, AbstractExpression index, Location location) {
        super(ExpressionType.INDEX, location);
        mIdentifier = identifier;
        mIndex = index;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        return TypeFactory.fromString(mIdentifier.evaluateType(abstractScope)
                .mName.replace("[", "").replace("]", ""));
    }

    @Override
    public String toString() {
        return mIdentifier + "[" + mIndex + "]";
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {

    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
      mIdentifier.generate(currentAbstractScope, generator);
      generator.addInstruction("LIT 0 1"); // skip length
      generator.addInstruction("OPR 0 2");
      mIndex.generate(currentAbstractScope, generator);
      generator.addInstruction("OPR 0 2");
      generator.addInstruction("LDA 0 0");
    }
}
