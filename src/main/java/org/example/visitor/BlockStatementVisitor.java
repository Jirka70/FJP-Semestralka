package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.statement.AbstractBlockStatement;

public class BlockStatementVisitor extends IavaParserBaseVisitor<AbstractBlockStatement> {
    @Override
    public AbstractBlockStatement visitBlockStatement(IavaParser.BlockStatementContext ctx) {
        if (ctx.statement() != null) {
            return new StatementVisitor().visit(ctx.statement());
        }

        if (ctx.localVariableDeclaration() != null) {
            return new LocalVariableDeclarationVisitor().visit(ctx.localVariableDeclaration());
        }

        throw new IllegalStateException("Expected statement or local variable declaration, got: " + ctx.getText());
    }
}
