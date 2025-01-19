package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.codeGeneration.IGeneratable;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;

import java.util.ArrayList;
import java.util.List;

public class ExpressionList implements ISemanticallyAnalyzable, IGeneratable {
    public final List<AbstractExpression> mExpressions = new ArrayList<>();

    public ExpressionList(List<AbstractExpression> expressions) {
        mExpressions.addAll(expressions);
    }

    @Override
    public String toString() {
        return "ExpressionList: " + mExpressions;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        for (AbstractExpression expression : mExpressions) {
            expression.analyze(abstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        for (AbstractExpression expression : mExpressions) {
            expression.generate(currentAbstractScope, generator);

            // ignore expression return value
            AbstractType resultType;
            try {
                resultType = expression.evaluateType(currentAbstractScope);
            } catch (SemanticException e) {
                throw new RuntimeException(e); // should not happen in this phase
            }
            int resultSize = generator.typeSize(resultType.mName);
            generator.addInstruction("INT 0 " + (-resultSize));
        }
    }
}
