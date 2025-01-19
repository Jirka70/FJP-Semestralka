package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.BooleanType;
import org.example.util.Location;

public class TernaryExpression extends AbstractExpression {
    public final AbstractExpression mCondition;
    public final AbstractExpression mFirst;
    public final AbstractExpression mSecond;

    public TernaryExpression(AbstractExpression condition, AbstractExpression trueBranch,
                             AbstractExpression falseBranch, Location location) {
        super(ExpressionType.TERNARY, location);
        mCondition = condition;
        mFirst = trueBranch;
        mSecond = falseBranch;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        AbstractType conditionType = mCondition.evaluateType(abstractScope);
        if (!(conditionType instanceof BooleanType)) {
            throw new TypeMismatchException("Ternary expression expects a boolean type in condition " + mLocation);
        }

        AbstractType trueBranchType = mFirst.evaluateType(abstractScope);
        AbstractType falseBranchType = mSecond.evaluateType(abstractScope);

        if (!trueBranchType.isCompatibleWith(falseBranchType)) {
            throw new TypeMismatchException("Ternary expression expects two compatible types in both branches. On "
                    + mLocation);
        }

        return trueBranchType.combineWith(falseBranchType);
    }

    @Override
    public String toString() {
        return mCondition.toString() + "?" + mFirst.toString() + ":" + mSecond.toString();
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        mCondition.analyze(abstractScope);
        mFirst.analyze(abstractScope);
        mSecond.analyze(abstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    private static final String TRUE_EXPRESSION_LABEL_SUFFIX = "TRUE_EXPRESSION";
    private static final String TERNARY_EXPRESSION_END_LABEL_SUFFIX = "TERNARY_EXPRESSION_END";

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating ternary expression");
        PrefixExpression negExpr = new PrefixExpression(mCondition, ExpressionType.NEG, mLocation);
        negExpr.generate(currentAbstractScope, generator);
        generator.addInstruction("JMC 0 " + (mLocation + TRUE_EXPRESSION_LABEL_SUFFIX)); // skok, pokud mExpression platí

        mSecond.generate(currentAbstractScope, generator);
        generator.addInstruction("JMP 0 " + (mLocation + TERNARY_EXPRESSION_END_LABEL_SUFFIX));

        generator.addCodeLabel(mLocation + TRUE_EXPRESSION_LABEL_SUFFIX);
        mFirst.generate(currentAbstractScope, generator);

        generator.addCodeLabel((mLocation + TERNARY_EXPRESSION_END_LABEL_SUFFIX));
    }
}
