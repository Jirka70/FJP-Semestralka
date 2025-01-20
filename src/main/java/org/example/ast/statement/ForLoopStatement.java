package org.example.ast.statement;

import org.example.ast.expression.EmptyExpression;
import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.ForLoopDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
import org.example.util.Location;

public class ForLoopStatement extends AbstractStatement {
    private static final String FOR_LOOP_SYMBOL = "for";
    public final ForLoopControl mForLoopControl;
    public final AbstractStatement mBody;

    public ForLoopStatement(ForLoopControl forLoopControl, AbstractStatement body, Location location) {
        super(StatementType.FOR, location);
        mForLoopControl = forLoopControl;
        mBody = body;
    }

    @Override
    public String toString() {
        return mForLoopControl + "{ " + mBody + " }";
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractSymbol forLoopSymbol = new StatementSymbol(FOR_LOOP_SYMBOL, mLocation);
        AbstractScope forLoopAbstractScope = abstractScope.getChildScopeBySymbol(forLoopSymbol);

        if (forLoopAbstractScope == null) {
            throw new InvalidStatementException("For loop was not found on location " + mLocation);
        }

        mForLoopControl.analyze(forLoopAbstractScope);
        mBody.analyze(forLoopAbstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor forLoopDescriptor = new ForLoopDescriptor();
        AbstractScope forLoopAbstractScope = new BlockScope(currentAbstractScope, forLoopDescriptor);
        AbstractSymbol forLoopSymbol = new StatementSymbol(FOR_LOOP_SYMBOL, mLocation);
        currentAbstractScope.addChildScope(forLoopSymbol, forLoopAbstractScope);
        mForLoopControl.collectData(forLoopAbstractScope);
        mBody.collectData(forLoopAbstractScope);
    }

    public static final String FOR_LOOP_START_LABEL_SUFFIX = "for_loop_start";
    public static final String FOR_LOOP_END_LABEL_SUFFIX = "for_loop_end";

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating for loop");
        AbstractSymbol forLoopSymbol = new StatementSymbol(FOR_LOOP_SYMBOL, mLocation);
        AbstractScope forLoopAbstractScope = currentAbstractScope.getChildScopeBySymbol(forLoopSymbol);

        if (mForLoopControl.mInit != null)
            mForLoopControl.mInit.generate(forLoopAbstractScope, generator);
        generator.addCodeLabel(mLocation + FOR_LOOP_START_LABEL_SUFFIX);

        if (mForLoopControl.mEnd != null && !(mForLoopControl.mEnd instanceof EmptyExpression)) {
            mForLoopControl.mEnd.generate(forLoopAbstractScope, generator);
            generator.addInstruction("JMC 0 " + (mLocation + FOR_LOOP_END_LABEL_SUFFIX));
        }

        mBody.generate(forLoopAbstractScope, generator);
        if (mForLoopControl.mUpdate != null)
            mForLoopControl.mUpdate.generate(forLoopAbstractScope, generator);
        generator.addInstruction("JMP 0 " + (mLocation + FOR_LOOP_START_LABEL_SUFFIX));
        generator.addCodeLabel(mLocation + FOR_LOOP_END_LABEL_SUFFIX);
    }
}
