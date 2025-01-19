package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.VariableSymbol;
import org.example.util.Location;

public abstract class UnaryExpression extends AbstractExpression {
    public final AbstractExpression mExpression;

    public UnaryExpression(AbstractExpression expression, ExpressionType operator, Location location) {
        super(operator, location);
        mExpression = expression;
    }

    protected void generateIncExpression(AbstractScope currentAbstractScope, CodeGenerator generator) {
        LiteralExpression incLit = new LiteralExpression("1", LiteralExpression.LiteralType.DECIMAL_LITERAL, mLocation);
        BinaryExpression incExpr = new BinaryExpression(mExpression, incLit, ExpressionType.PLUS, mLocation);
        BinaryExpression assignExpr = new BinaryExpression(mExpression, incExpr, ExpressionType.ASSIGN, mLocation);
        assignExpr.generate(currentAbstractScope, generator);
    }

    protected void generateDecExpression(AbstractScope currentAbstractScope, CodeGenerator generator) {
        LiteralExpression incLit = new LiteralExpression("1", LiteralExpression.LiteralType.DECIMAL_LITERAL, mLocation);
        BinaryExpression decExpr = new BinaryExpression(mExpression, incLit, ExpressionType.MINUS, mLocation);
        BinaryExpression assignExpr = new BinaryExpression(mExpression, decExpr, ExpressionType.ASSIGN, mLocation);
        assignExpr.generate(currentAbstractScope, generator);
    }

    protected int expressionTypeSize(AbstractScope currentAbstractScope, CodeGenerator generator) {
        VariableSymbol symbol = new VariableSymbol(((IdentifierExpression) mExpression).mIdentifier);
        AbstractDescriptor descriptor = currentAbstractScope.getSymbolDescriptorOnLocation(symbol, mLocation);
        return generator.typeSize(((VariableDescriptor) descriptor).mType);
    }
}
