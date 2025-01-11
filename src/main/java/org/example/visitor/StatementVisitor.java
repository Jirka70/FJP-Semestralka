package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.expression.EmptyExpression;
import org.example.statement.*;
import org.example.primitive.expression.AbstractExpression;
import org.example.statement.ifStatement.ElseStatement;
import org.example.statement.ifStatement.IfStatement;

public class StatementVisitor extends IavaParserBaseVisitor<AbstractStatement> {

    @Override
    public AbstractStatement visitStatement(IavaParser.StatementContext ctx) {
        if (isIfStatement(ctx)) {
            return extractIfStatement(ctx);
        } else if (isForStatement(ctx)) {
            return extractForStatement(ctx);
        } else if (isBlockStatement(ctx)) {
            return extractBlockStatement(ctx);
        } else if (isExpressionStatement(ctx)) {
            return extractExpressionStatement(ctx);
        } else if (isKeywordStatement(ctx)) {
            return extractKeywordStatement(ctx);
        } else if (isEmptyStatement(ctx)) {
            return extractEmptyStatement(ctx);
        } else if (isReturnStatement(ctx)) {
            return extractReturnStatement(ctx);
        }

        throw new IllegalArgumentException("Type of statement " + ctx.getText() + " not recognized");
    }

    private ForLoopStatement extractForStatement(IavaParser.StatementContext ctx) {
        new ForStatementVisitor().visit(ctx);
        return null;
    }

    private boolean isForStatement(IavaParser.StatementContext ctx) {
        return ctx.FOR() != null;
    }

    private IfStatement extractIfStatement(IavaParser.StatementContext ctx) {
        IavaParser.ParExpressionContext parExpressionContext = ctx.parExpression();
        AbstractExpression parExpression = new ExpressionVisitor().visit(parExpressionContext);
        AbstractStatement ifBody = extractIfBody(ctx);
        ElseStatement elseStatement = isElseStatement(ctx)
                ? extractElseStatement(ctx)
                : null;

        return new IfStatement(parExpression, ifBody, elseStatement);
    }

    private AbstractStatement extractIfBody(IavaParser.StatementContext ctx) {
        int ifBodyIndex = 0;

        IavaParser.StatementContext ifBodyStatementContext = ctx.statement(ifBodyIndex);
        return visit(ifBodyStatementContext);
    }


    private ElseStatement extractElseStatement(IavaParser.StatementContext ctx) {
        int elseBodyIndex = 1;

        if (ctx.statement().size() < 2) {
            return null;
        }

        IavaParser.StatementContext elseBodyStatement = ctx.statement(elseBodyIndex);
        AbstractStatement elseIfBody = visit(elseBodyStatement);
        return new ElseStatement(elseIfBody);
    }

    private boolean isIfStatement(IavaParser.StatementContext ctx) {
        return ctx.IF() != null;
    }

    private boolean isElseStatement(IavaParser.StatementContext ctx) {
        return ctx.ELSE() != null;
    }

    private boolean isBlockStatement(IavaParser.StatementContext ctx) {
        return ctx.blockLabel != null;
    }

    private Block extractBlockStatement(IavaParser.StatementContext ctx) {
        return new BlockVisitor().visit(ctx);
    }

    private boolean isExpressionStatement(IavaParser.StatementContext ctx) {
        return ctx.statementExpression != null;
    }

    private ExpressionStatement extractExpressionStatement(IavaParser.StatementContext ctx) {
        AbstractExpression expression = new ExpressionVisitor().visit(ctx.statementExpression);
        return new ExpressionStatement(expression);
    }

    private boolean isKeywordStatement(IavaParser.StatementContext ctx) {
        return ctx.BREAK() != null || ctx.CONTINUE() != null;
    }

    private KeywordStatement extractKeywordStatement(IavaParser.StatementContext ctx) {
        KeywordStatement.StatementKeywordType type = null;
        if (ctx.BREAK() != null) {
            type = KeywordStatement.StatementKeywordType.BREAK;
        } else if (ctx.CONTINUE() != null) {
            type = KeywordStatement.StatementKeywordType.CONTINUE;
        }
        return new KeywordStatement(type);
    }

    private boolean isEmptyStatement(IavaParser.StatementContext ctx) {
        return ctx.SEMI() != null && ctx.getChildCount() == 1;
    }

    private AbstractStatement extractEmptyStatement(IavaParser.StatementContext ignoredCtx) {
       return null;
    }
    private boolean isReturnStatement(IavaParser.StatementContext ctx) {
        return ctx.RETURN() != null;
    }

    private ReturnStatement extractReturnStatement(IavaParser.StatementContext ctx) {
        AbstractExpression expression = ctx.expression() == null
            ? new EmptyExpression()
            : new ExpressionVisitor().visit(ctx.expression());
        return new ReturnStatement(expression);
    }
}
