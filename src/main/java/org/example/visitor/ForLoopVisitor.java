package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.statement.ForLoopStatement;

public class ForLoopVisitor extends IavaParserBaseVisitor<ForLoopStatement> {

    @Override
    public ForLoopStatement visitForInit(IavaParser.ForInitContext ctx) {
        return super.visitForInit(ctx);
    }


}
