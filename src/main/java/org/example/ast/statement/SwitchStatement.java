package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.BinaryExpression;
import org.example.ast.expression.ExpressionType;
import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.SwitchDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class SwitchStatement extends AbstractStatement {
    private static final String SWITCH_KEYWORD = "switch";
    public final AbstractExpression mExpression;
    public final List<SwitchCase> mCases = new ArrayList<>();

    public SwitchStatement(AbstractExpression expression, List<SwitchCase> cases, Location location) {
        super(StatementType.SWITCH, location);
        mExpression = expression;
        if (cases == null || cases.isEmpty())
            throw new IllegalArgumentException("Switch statement must have at least one switch case");
        mCases.addAll(cases);
    }

    @Override
    public String toString() {
        return "Switch Statement: " + mExpression + " " + mCases;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractSymbol switchSymbol = new StatementSymbol(SWITCH_KEYWORD, mLocation);
        AbstractScope switchAbstractScope = abstractScope.getChildScopeBySymbol(switchSymbol);

        mExpression.analyze(abstractScope);

        if (switchAbstractScope == null) {
            throw new InvalidStatementException("Switch statement was not found on location " + mLocation);
        }

        for (SwitchCase cases : mCases) {
            cases.analyze(switchAbstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor switchDescriptor = new SwitchDescriptor();
        AbstractScope switchAbstractScope = new BlockScope(currentAbstractScope, switchDescriptor);
        AbstractSymbol switchSymbol = new StatementSymbol(SWITCH_KEYWORD, mLocation);
        currentAbstractScope.addChildScope(switchSymbol, switchAbstractScope);
        for (SwitchCase cases : mCases) {
            cases.collectData(switchAbstractScope);
        }
    }

    public static final String SWITCH_END_LABEL_SUFFIX = "switch_end";
    private static final String SWITCH_CASE_LABEL_COMMON = "switch_case_";

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating switch");
        AbstractSymbol switchSymbol = new StatementSymbol(SWITCH_KEYWORD, mLocation);
        AbstractScope switchAbstractScope = currentAbstractScope.getChildScopeBySymbol(switchSymbol);

        // generate expressions
        SwitchCase defaultCase = null;
        for (int i = 0; i < mCases.size(); i++) {
            SwitchCase mCase = mCases.get(i);

            if (mCase.mExpression != null) {
                new BinaryExpression(mExpression, mCase.mExpression, ExpressionType.NOT_EQUAL_TO, mLocation)
                        .generate(switchAbstractScope, generator);
                generator.addInstruction("JMC 0 " + (mLocation + SWITCH_CASE_LABEL_COMMON + i));

            } else { // null expression means default case and will be generated after all others
                defaultCase = mCase;
            }
        }
        if (defaultCase != null) {
            generator.addInstruction("JMP 0 " + (mLocation + SWITCH_CASE_LABEL_COMMON + mCases.indexOf(defaultCase)));
        }

        // generate bodies
        for (int i = 0; i < mCases.size(); i++) {
            SwitchCase mCase = mCases.get(i);
            generator.addCodeLabel(mLocation + SWITCH_CASE_LABEL_COMMON + i);
            for (AbstractBlockStatement caseStatement : mCase.mBody) {
                caseStatement.generate(switchAbstractScope,generator);
            }
        }

        generator.addCodeLabel(mLocation + SWITCH_END_LABEL_SUFFIX);
    }
}
