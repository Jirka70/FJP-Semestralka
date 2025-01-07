package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.expression.AbstractExpression;

public class VariableVisitor extends IavaParserBaseVisitor<VariableVisitor> {

    @Override
    public VariableVisitor visitVariableInitializer(IavaParser.VariableInitializerContext ctx) {
        System.out.println("variable initializer " + ctx.getText());

        AbstractExpression variableExpression = new ExpressionVisitor().visit(ctx.expression());
        System.out.println("Expression: " + ctx.expression().getText());
        return super.visitVariableInitializer(ctx);
    }
}
