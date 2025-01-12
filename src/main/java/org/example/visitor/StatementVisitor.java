package org.example.visitor;

import org.antlr.v4.runtime.tree.ParseTree;
import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.expression.EmptyExpression;
import org.example.ast.statement.*;
import org.example.ast.expression.AbstractExpression;
import org.example.ast.statement.ifStatement.ElseStatement;
import org.example.ast.statement.ifStatement.IfStatement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        } else if (isSwitchStatement(ctx)) {
            return extractSwitchStatement(ctx);
        } else if (isDoWhileStatement(ctx)) {
            return extractDoWhileStatement(ctx);
        } else if (isWhileStatement(ctx)) {
            return extractWhileStatement(ctx);
        }

        throw new IllegalArgumentException("Type of statement " + ctx.getText() + " not recognized");
    }

    private ForLoopStatement extractForStatement(IavaParser.StatementContext ctx) {
        int forLoopyBodyStatementIndex = 0;
        int forLoopControlStatementIndex = 2;
        IavaParser.StatementContext forLoopBodyStatementCtx = ctx.statement(forLoopyBodyStatementIndex);
        Block forLoopBody = new BlockVisitor().visit(forLoopBodyStatementCtx);

        ParseTree forLoopControlTree = ctx.children.get(forLoopControlStatementIndex);

        ForLoopControl forLoopControl = new ForLoopControlVisitor().visit(forLoopControlTree);
        return new ForLoopStatement(forLoopControl, forLoopBody);
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

    private boolean isSwitchStatement(IavaParser.StatementContext ctx) {
        return ctx.SWITCH() != null;
    }

    private SwitchStatement extractSwitchStatement(IavaParser.StatementContext ctx) {
        AbstractExpression parExpression = new ExpressionVisitor().visit(ctx.parExpression());
        List<SwitchCase> cases = extractNonEmptySwitchCases(ctx);
        cases.addAll(extractEmptySwitchCases(ctx));

        return new SwitchStatement(parExpression, cases);
    }

    private List<SwitchCase> extractNonEmptySwitchCases(IavaParser.StatementContext ctx) {
        List<SwitchCase> cases = new ArrayList<>();

        for (IavaParser.SwitchBlockStatementGroupContext group : ctx.switchBlockStatementGroup()) {
            List<AbstractBlockStatement> groupStatements = new ArrayList<>();
            for (IavaParser.BlockStatementContext blockCtx : group.blockStatement()) {
                groupStatements.add(visit(blockCtx));
            }

            for (IavaParser.SwitchLabelContext lbl : group.switchLabel()) {
                AbstractExpression caseExpression = lbl.constantExpression == null
                        ? null
                        : new ExpressionVisitor().visit(lbl.constantExpression);

                SwitchCase swCase = new SwitchCase(caseExpression, groupStatements);
                cases.add(swCase);
            }
        }
        return cases;
    }

    private List<SwitchCase> extractEmptySwitchCases(IavaParser.StatementContext ctx) {
        List<SwitchCase> cases = new ArrayList<>();
        for (IavaParser.SwitchLabelContext lbl : ctx.switchLabel()) {
            AbstractExpression caseExpression = lbl.constantExpression == null
                    ? null
                    : new ExpressionVisitor().visit(lbl.constantExpression);

            SwitchCase swCase = new SwitchCase(caseExpression, Collections.emptyList());
            cases.add(swCase);
        }
        return cases;
    }

    private boolean isDoWhileStatement(IavaParser.StatementContext ctx) {
        return ctx.DO() != null;
    }

    private DoWhileStatement extractDoWhileStatement(IavaParser.StatementContext ctx) {
        AbstractStatement statement = visit(ctx.statement(0));
        AbstractExpression parExpression = new ExpressionVisitor().visit(ctx.parExpression());
        return new DoWhileStatement(statement, parExpression);
    }

    private boolean isWhileStatement(IavaParser.StatementContext ctx) {
        return ctx.WHILE() == ctx.getChild(0);
    }

    private WhileStatement extractWhileStatement(IavaParser.StatementContext ctx) {
        AbstractExpression parExpression = new ExpressionVisitor().visit(ctx.parExpression());
        AbstractStatement statement = visit(ctx.statement(0));
        return new WhileStatement(parExpression, statement);
    }
}
