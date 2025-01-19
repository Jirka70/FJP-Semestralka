package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.ExpressionType;
import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.util.Location;

public class ExpressionStatement extends AbstractStatement {
    public final AbstractExpression mExpression;

    public ExpressionStatement(AbstractExpression expression, Location location) {
        super(StatementType.EXPRESSION, location);
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "Expression Statement: " + mExpression;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        mExpression.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentScope, CodeGenerator generator) {
        mExpression.generate(currentScope, generator);

//        if (mExpression.mExpressionType.equals(ExpressionType.METHOD_CALL)) {
//            System.out.println("Removing ignored return value");
//            // TODO: ignored return value must be ignored
//        }

        // ignore expression return value
        AbstractType resultType;
        try {
            resultType = mExpression.evaluateType(currentScope);
        } catch (SemanticException e) {
            throw new RuntimeException(e); // should not happen in this phase
        }
        int resultSize = generator.typeSize(resultType.mName);
        generator.addInstruction("INT 0 " + (-resultSize));
    }
}
