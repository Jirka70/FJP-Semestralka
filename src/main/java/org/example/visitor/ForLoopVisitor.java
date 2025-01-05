package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.ForLoop;

public class ForLoopVisitor extends IavaParserBaseVisitor<ForLoop> {

    @Override
    public ForLoop visitForInit(IavaParser.ForInitContext ctx) {
        visit(ctx);
        return super.visitForInit(ctx);
    }
}
