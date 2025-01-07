package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.variable.Modifier;

public class ModifierVisitor extends IavaParserBaseVisitor<Modifier> {

    @Override
    public Modifier visitModifier(IavaParser.ModifierContext ctx) {
        String modifier = ctx.getText();
        return new Modifier(modifier);
    }
}
