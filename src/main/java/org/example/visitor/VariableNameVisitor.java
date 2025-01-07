package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;

public class VariableNameVisitor extends IavaParserBaseVisitor<String> {

    // FIXME separate variable name correctly
    @Override
    public String visitVariableDeclarators(IavaParser.VariableDeclaratorsContext ctx) {
        System.out.println(ctx.variableDeclarator(0).getText());
        return ctx.getText();
    }
}
