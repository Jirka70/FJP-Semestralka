package org.example.ast.statement;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.DescriptorType;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
import org.example.util.Location;

import java.util.Arrays;

import org.example.ast.statement.StatementType;

public class KeywordStatement extends AbstractStatement {
    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        if (mType == StatementKeywordType.BREAK) {
            analyzeBreak(abstractScope);
        } else if (mType == StatementKeywordType.CONTINUE) {
            analyzeContinue(abstractScope);
        }
    }

    private void analyzeContinue(AbstractScope abstractScope) throws SemanticException {
        AbstractScope currentScope = abstractScope;

        while (currentScope.hasParent()) {
            AbstractDescriptor currentSCopeDescriptor = currentScope.mScopeDescriptor;
            DescriptorType currentScopeDescriptorType = currentSCopeDescriptor.mDescriptorType;
            boolean camBeApplied = switch (currentScopeDescriptorType) {
                case FOR_LOOP, WHILE_LOOP, DO_WHILE -> true;
                default -> false;
            };

            if (camBeApplied) {
                return;
            }

            currentScope = currentScope.mParentAbstractScope;
        }

        throw new InvalidStatementException("Continue keyword cannot be used outside of loop on " + mLocation);
    }

    private void analyzeBreak(AbstractScope abstractScope) throws SemanticException {
        AbstractScope currentScope = abstractScope;

        while (currentScope.hasParent()) {
            AbstractDescriptor currentSCopeDescriptor = currentScope.mScopeDescriptor;
            DescriptorType currentScopeDescriptorType = currentSCopeDescriptor.mDescriptorType;
            boolean camBeApplied = switch (currentScopeDescriptorType) {
                case FOR_LOOP, WHILE_LOOP, DO_WHILE, SWITCH -> true;
                default -> false;
            };

            if (camBeApplied) {
                return;
            }

            currentScope = currentScope.mParentAbstractScope;
        }

        throw new InvalidStatementException("Break keyword cannot be used outside of loop or switch on " + mLocation);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    public enum StatementKeywordType {
        BREAK,
        CONTINUE,
    }

    public final StatementKeywordType mType;

    public KeywordStatement(StatementKeywordType type, Location location) {
        super(StatementType.KEYWORD, location);
        if (type == null)
            throw new IllegalArgumentException("Keyword statement must be one of " +
                    Arrays.toString(StatementKeywordType.values()));
        mType = type;
    }

    @Override
    public String toString() {
        return mType.toString();
    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        AbstractScope tmpScope = currentAbstractScope;
        DescriptorType tmpDescriptorType = tmpScope.mScopeDescriptor.mDescriptorType;
        while (true) {
            boolean shouldBreakLoop = switch (tmpDescriptorType) {
                case FOR_LOOP, WHILE_LOOP, DO_WHILE, SWITCH -> true;
                default -> false;
            };

            if (shouldBreakLoop) break;
            else {
                tmpScope = tmpScope.mParentAbstractScope;
                tmpDescriptorType = tmpScope.mScopeDescriptor.mDescriptorType;
            }
        }
        StatementSymbol statementSymbol = (StatementSymbol) tmpScope.getSymbol();

        String labelSuffix = null;
        if (mType.equals(StatementKeywordType.BREAK)) {
            switch (tmpDescriptorType) {
                case FOR_LOOP -> labelSuffix = ForLoopStatement.FOR_LOOP_END_LABEL_SUFFIX;
                case WHILE_LOOP -> labelSuffix = WhileStatement.WHILE_LOOP_END_LABEL_SUFFIX;
                case DO_WHILE -> labelSuffix = DoWhileStatement.DO_WHILE_LOOP_END_LABEL_SUFFIX;
                case SWITCH -> labelSuffix = SwitchStatement.SWITCH_END_LABEL_SUFFIX;
            }
        } else if (mType.equals(StatementKeywordType.CONTINUE)) {
            switch (tmpDescriptorType) {
                case FOR_LOOP -> labelSuffix = ForLoopStatement.FOR_LOOP_START_LABEL_SUFFIX;
                case WHILE_LOOP -> labelSuffix = WhileStatement.WHILE_LOOP_START_LABEL_SUFFIX;
                case DO_WHILE -> labelSuffix = DoWhileStatement.DO_WHILE_LOOP_START_LABEL_SUFFIX;
                case SWITCH -> throw new RuntimeException(new InvalidStatementException("Continue inside switch statement"));
            }
        }
        generator.addInstruction("JMP 0 " + (statementSymbol.mLocation + labelSuffix));
    }
}
