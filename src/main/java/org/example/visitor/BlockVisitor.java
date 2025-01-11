package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.statement.Block;
import org.example.statement.AbstractBlockStatement;

import java.util.List;
import java.util.ArrayList;

public class BlockVisitor extends IavaParserBaseVisitor<Block> {
    @Override
    public Block visitBlock(IavaParser.BlockContext ctx) {
        List<AbstractBlockStatement> blockStatements = new ArrayList<>();

        List<IavaParser.BlockStatementContext> blockStatementContextList = ctx.blockStatement();
        for (IavaParser.BlockStatementContext blockStatementContext : blockStatementContextList) {
            AbstractBlockStatement blockStatement = new BlockStatementVisitor().visit(blockStatementContext);
            blockStatements.add(blockStatement);
        }

        return new Block(blockStatements);
    }
}
