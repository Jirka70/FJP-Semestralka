package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.statement.Block;

public class MethodBodyVisitor extends IavaParserBaseVisitor<Block> {
    @Override
    public Block visitMethodBody(IavaParser.MethodBodyContext ctx) {
        IavaParser.BlockContext blockContext = ctx.block();

        return blockContext == null
                ? null
                : new BlockVisitor().visit(blockContext);
    }
}
