package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.block.Block;
import org.example.primitive.block.EmptyBlock;
import org.example.primitive.clazz.method.MethodBody;

public class MethodBodyVisitor extends IavaParserBaseVisitor<MethodBody> {
    @Override
    public MethodBody visitMethodBody(IavaParser.MethodBodyContext ctx) {
        IavaParser.BlockContext blockContext = ctx.block();

        Block block = blockContext == null
                ? new EmptyBlock()
                : new BlockVisitor().visit(blockContext);

        return new MethodBody(block);
    }
}
