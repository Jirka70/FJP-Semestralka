package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.statement.AbstractBlockStatement;
import org.example.ast.statement.Block;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class BlockVisitor extends IavaParserBaseVisitor<Block> {
    @Override
    public Block visitBlock(IavaParser.BlockContext ctx) {
        List<AbstractBlockStatement> blockStatements = new ArrayList<>();

        List<IavaParser.BlockStatementContext> blockStatementContextList = ctx.blockStatement();
        for (IavaParser.BlockStatementContext blockStatementContext : blockStatementContextList) {
            AbstractBlockStatement blockStatement = new BlockStatementVisitor().visit(blockStatementContext);
            blockStatements.add(blockStatement);
        }

        Location location = getBlockLocation(ctx);
        return new Block(blockStatements, location);
    }

    private Location getBlockLocation(IavaParser.BlockContext ctx) {
        return new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }
}
