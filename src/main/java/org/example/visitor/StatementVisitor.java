package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.block.Block;
import org.example.primitive.block.EmptyBlock;
import org.example.primitive.expression.AbstractExpression;
import org.example.statement.AbstractStatement;
import org.example.statement.ifStatement.ElseStatement;
import org.example.statement.ifStatement.IfStatement;

public class StatementVisitor extends IavaParserBaseVisitor<AbstractStatement> {

    @Override
    public AbstractStatement visitStatement(IavaParser.StatementContext ctx) {
        if (isIfStatement(ctx)) {
            return extractIfStatement(ctx);
        }
        return super.visitStatement(ctx);
    }

    private IfStatement extractIfStatement(IavaParser.StatementContext ctx) {
        IavaParser.ParExpressionContext parExpressionContext = ctx.parExpression();
        AbstractExpression parExpression = new ExpressionVisitor().visit(parExpressionContext);
        Block ifBody = extractIfBody(ctx);
        ElseStatement elseStatement = isElseStatement(ctx)
                ? extractElseStatement(ctx)
                : null;

        return new IfStatement(parExpression, ifBody, elseStatement);
    }

    @Override
    public AbstractStatement visitExpression(IavaParser.ExpressionContext ctx) {
        System.out.println("expr: " + ctx.getText());
        return super.visitExpression(ctx);
    }

    private Block extractIfBody(IavaParser.StatementContext ctx) {
        int ifBodyIndex = 0;

        IavaParser.StatementContext ifBodyStatementContext = ctx.statement(ifBodyIndex);
        if (ifBodyStatementContext == null || ifBodyStatementContext.isEmpty()) {
            return new EmptyBlock();
        }

        for (IavaParser.StatementContext statementContext : ctx.statement()) {
            return new BlockVisitor().visit(statementContext.blockLabel);
        }

        throw new IllegalStateException("Expected statement inside if body, got: " + ctx
                .statement()
                .get(ifBodyIndex)
                .getText());
    }


    private ElseStatement extractElseStatement(IavaParser.StatementContext ctx) {
        int elseBodyIndex = 1;

        if (ctx.statement().size() < 2) {
            return new ElseStatement(new EmptyBlock());
        }

        IavaParser.StatementContext elseBodyStatement = ctx.statement(elseBodyIndex);
        if (elseBodyStatement == null || elseBodyStatement.isEmpty()) {
            return new ElseStatement(new EmptyBlock());
        }

        Block elseIfBody = new BlockVisitor().visit(elseBodyStatement.blockLabel);
        return new ElseStatement(elseIfBody);
    }

    private boolean isIfStatement(IavaParser.StatementContext ctx) {
        return ctx.IF() != null;
    }

    private boolean isElseStatement(IavaParser.StatementContext ctx) {
        return ctx.ELSE() != null;
    }
}
