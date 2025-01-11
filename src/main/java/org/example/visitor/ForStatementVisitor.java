package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.statement.ForLoopStatement;
import org.example.statement.LocalVariable;
import org.example.statement.LocalVariableDeclaration;

import java.util.List;

public class ForStatementVisitor extends IavaParserBaseVisitor<ForLoopStatement> {


    @Override
    public ForLoopStatement visitForControl(IavaParser.ForControlContext ctx) {
        System.out.println("for: " + ctx.getText());

        IavaParser.ForInitContext forInitContext = ctx.forInit();
        IavaParser.ExpressionListContext forUpdateExpressionList = ctx.forUpdate;
        IavaParser.ExpressionContext forStopExpression = ctx.expression();

        LocalVariableDeclaration forInitVar =
                new LocalVariableDeclarationVisitor().visit(forInitContext.localVariableDeclaration());
        System.out.println(forInitContext.expressionList());

        System.out.println("forinit: " + null);
        System.out.println("forupdate: " + ctx.forUpdate.getText());


        System.out.println("stop: " + ctx.expression().getText());
        IavaParser.ExpressionListContext expressionListContext = ctx.expressionList();
        List<IavaParser.ExpressionContext> expressionContextList = expressionListContext.expression();

        for (IavaParser.ExpressionContext expressionContext : expressionContextList) {
            System.out.println("expression: " + expressionContext.getText());
        }

        return super.visitForControl(ctx);
    }
}
