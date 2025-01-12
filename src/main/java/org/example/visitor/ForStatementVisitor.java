package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.expression.AbstractExpression;
import org.example.primitive.expression.EmptyExpression;
import org.example.primitive.expression.ExpressionList;
import org.example.statement.ForLoopStatement;
import org.example.statement.LocalVariableDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ForStatementVisitor extends IavaParserBaseVisitor<ForLoopStatement> {


    @Override
    public ForLoopStatement visitForControl(IavaParser.ForControlContext ctx) {
        IavaParser.ForInitContext forInitContext = ctx.forInit();

        LocalVariableDeclaration forInit = forInitContext == null
            ? createEmptyLocalVariableDeclaration()
            : new LocalVariableDeclarationVisitor().visit(forInitContext.localVariableDeclaration());

        AbstractExpression forStopExpression = ctx.expression() == null
            ? new EmptyExpression()
            : extractExpression(ctx.expression());

        ExpressionList forUpdate = ctx.forUpdate == null
            ? new ExpressionList(new ArrayList<>())
            : extractExpressions(ctx.forUpdate);

        System.out.println("forInit: " + forInit);
        System.out.println("forStop: " + forStopExpression);
        System.out.println("forUpate: " + forUpdate);

        return null;
    }

    private LocalVariableDeclaration createEmptyLocalVariableDeclaration() {
        return null;
    }

    private AbstractExpression createEmptyExpression() {
        return new EmptyExpression();
    }

    private ExpressionList extractExpressions(IavaParser.ExpressionListContext expressionListContext) {
        List<AbstractExpression> abstractExpressions = new ArrayList<>();

        for (IavaParser.ExpressionContext expressionContext : expressionListContext.expression()) {
            AbstractExpression expression = extractExpression(expressionContext);
            abstractExpressions.add(expression);
        }

        return new ExpressionList(abstractExpressions);
    }

    private AbstractExpression extractExpression(IavaParser.ExpressionContext expressionContext) {
        return new ExpressionVisitor().visit(expressionContext);
    }
}
