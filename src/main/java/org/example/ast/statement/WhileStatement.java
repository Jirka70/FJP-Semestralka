package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.WhileLoopDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.BooleanType;
import org.example.util.Location;


public class WhileStatement extends AbstractStatement {
    private static final String WHILE_KEYWORD = "while";
    public final AbstractExpression mExpression;
    public final AbstractStatement mBody;

    public WhileStatement(AbstractExpression expression, AbstractStatement body, Location location) {
        super(StatementType.WHILE, location);
        mExpression = expression;
        mBody = body;
    }

    @Override
    public String toString() {
        return "while (" + mExpression + ") " +  mBody;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractSymbol symbol = new StatementSymbol(WHILE_KEYWORD, mLocation);
        mExpression.analyze(abstractScope);

        AbstractType expressionType = mExpression.evaluateType(abstractScope);
        if (!(expressionType instanceof BooleanType)) {
            throw new TypeMismatchException("While statement cannot contain non-boolean statement on " + mLocation);
        }

        AbstractScope whileAbstractScope = abstractScope.getChildScopeBySymbol(symbol);

        if (whileAbstractScope == null) {
            throw new InvalidStatementException("While statement was not found on location " + mLocation);
        }

        mBody.analyze(whileAbstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor abstractDescriptor = new WhileLoopDescriptor();
        AbstractScope whileAbstractScope = new BlockScope(currentAbstractScope, abstractDescriptor);
        AbstractSymbol whileSymbol = new StatementSymbol(WHILE_KEYWORD, mLocation);
        currentAbstractScope.addChildScope(whileSymbol, whileAbstractScope);

        mBody.collectData(whileAbstractScope);
    }

    public static final String WHILE_LOOP_START_LABEL_SUFFIX = "while_loop_start";
    public static final String WHILE_LOOP_END_LABEL_SUFFIX = "while_loop_end";
    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating while loop");
        AbstractSymbol symbol = new StatementSymbol(WHILE_KEYWORD, mLocation);
        AbstractScope whileAbstractScope = currentAbstractScope.getChildScopeBySymbol(symbol);

        generator.addCodeLabel(mLocation + WHILE_LOOP_START_LABEL_SUFFIX);
        mExpression.generate(whileAbstractScope, generator);
        generator.addInstruction("JMC 0 " + (mLocation + WHILE_LOOP_END_LABEL_SUFFIX));

        mBody.generate(whileAbstractScope, generator);
        generator.addInstruction("JMP 0 " + (mLocation + WHILE_LOOP_START_LABEL_SUFFIX));
        generator.addCodeLabel(mLocation + WHILE_LOOP_END_LABEL_SUFFIX);
    }
}
