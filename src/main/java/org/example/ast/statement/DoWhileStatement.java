package org.example.ast.statement;

import org.example.ast.expression.AbstractExpression;
import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.InvalidStatementException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.DoWhileDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.BlockScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.StatementSymbol;
import org.example.semantic.type.AbstractType;
import org.example.semantic.type.BooleanType;
import org.example.util.Location;


public class DoWhileStatement extends AbstractStatement {
    private static final String WHILE_SYMBOL = "while";
    public final AbstractStatement mBody;
    public final AbstractExpression mExpression;

    public DoWhileStatement(AbstractStatement body, AbstractExpression expression, Location location) {
        super(StatementType.DO_WHILE, location);
        mBody = body;
        mExpression = expression;
    }

    @Override
    public String toString() {
        return "do " +
                mBody +
                " while " + "(" + mExpression + ");";
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        AbstractSymbol doWhileSymbol = new StatementSymbol(WHILE_SYMBOL, mLocation);
        AbstractScope doWhileAbstractScope = abstractScope.getChildScopeBySymbol(doWhileSymbol);

        if (doWhileAbstractScope == null) {
            throw new InvalidStatementException("Do while statement on location " + mLocation + " was not found");
        }

        AbstractType expressionType = mExpression.evaluateType(abstractScope);
        mExpression.analyze(abstractScope);

        if (!(expressionType instanceof BooleanType)) {
            throw new TypeMismatchException("Expression in while has to be in boolean type on " + mLocation);
        }

        mBody.analyze(doWhileAbstractScope);
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor doWhileDescriptor = new DoWhileDescriptor();
        AbstractScope doWhileAbstractScope = new BlockScope(currentAbstractScope, doWhileDescriptor);
        AbstractSymbol whileSymbol = new StatementSymbol(WHILE_SYMBOL, mLocation);
        currentAbstractScope.addChildScope(whileSymbol, doWhileAbstractScope);
        mBody.collectData(doWhileAbstractScope);
    }

    private static final String DO_WHILE_LOOP_START_LABEL_SUFFIX = "do_while_loop_start";
    private static final String DO_WHILE_LOOP_END_LABEL_SUFFIX = "do_while_loop_end";

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating do-while loop");
        AbstractSymbol doWhileSymbol = new StatementSymbol(WHILE_SYMBOL, mLocation);
        AbstractScope doWhileAbstractScope = currentAbstractScope.getChildScopeBySymbol(doWhileSymbol);

        mBody.generate(doWhileAbstractScope, generator);
        generator.addCodeLabel(mLocation + DO_WHILE_LOOP_START_LABEL_SUFFIX);
        mExpression.generate(doWhileAbstractScope, generator);
        generator.addInstruction("JMC 0 " + (mLocation + DO_WHILE_LOOP_END_LABEL_SUFFIX));

        mBody.generate(doWhileAbstractScope, generator);
        generator.addInstruction("JMP 0 " + (mLocation + DO_WHILE_LOOP_START_LABEL_SUFFIX));
        generator.addCodeLabel(mLocation + DO_WHILE_LOOP_END_LABEL_SUFFIX);
    }
}
