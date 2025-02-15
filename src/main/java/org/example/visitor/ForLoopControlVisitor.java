package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.expression.AbstractExpression;
import org.example.ast.expression.EmptyExpression;
import org.example.ast.expression.ExpressionList;
import org.example.ast.statement.ForInit;
import org.example.ast.statement.ForLoopControl;
import org.example.ast.statement.LocalVariableDeclaration;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class ForLoopControlVisitor extends IavaParserBaseVisitor<ForLoopControl> {


    @Override
    public ForLoopControl visitForControl(IavaParser.ForControlContext ctx) {
        IavaParser.ForInitContext forInitContext = ctx.forInit();

        ForInit forInit = forInitContext == null
            ? createEmptyForInit()
            : extractForInit(ctx);

        AbstractExpression forStopExpression = ctx.expression() == null
            ? createEmptyExpression(ctx)
            : extractExpression(ctx.expression());

        ExpressionList forUpdate = ctx.forUpdate == null
            ? createEmptyExpressionList()
            : extractExpressions(ctx.forUpdate);

        //System.out.println("forInit: " + forInit);

        Location location = getExpressionLocation(ctx);
        return new ForLoopControl(forInit, forStopExpression, forUpdate, location);
    }

    private ForInit createEmptyForInit() {
        return null;
    }

    private ForInit extractForInit(IavaParser.ForControlContext ctx) {
        IavaParser.ForInitContext forInitContext = ctx.forInit();

        ExpressionList expressionList = forInitContext.expressionList() == null
            ? null
            : extractExpressions(forInitContext.expressionList());

        LocalVariableDeclaration localVariableDeclaration = forInitContext.localVariableDeclaration() == null
                ? null
                : new LocalVariableDeclarationVisitor().visit(forInitContext.localVariableDeclaration());

        return new ForInit(expressionList, localVariableDeclaration);

    }


    private AbstractExpression createEmptyExpression(IavaParser.ForControlContext ctx) {
        Location location = getExpressionLocation(ctx);
        return new EmptyExpression(location);
    }

    private Location getExpressionLocation(IavaParser.ForControlContext ctx) {
        return new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    private ExpressionList createEmptyExpressionList() {
        return new ExpressionList(new ArrayList<>());
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
