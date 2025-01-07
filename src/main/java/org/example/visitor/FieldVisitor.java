package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.clazz.FieldPrimitive;
import org.example.primitive.variable.Modifier;

/**
 * Visitor for fields in classes
 */
// FIXME modifiers (finalis) are not recognized correctly
public class FieldVisitor extends IavaParserBaseVisitor<FieldPrimitive> {

    // TODO when it works properly, use this class in ClassVisitor for adding fields to ClassPrimitive
    @Override
    public FieldPrimitive visitFieldDeclaration(IavaParser.FieldDeclarationContext ctx) {
        Modifier fieldModifier = new ModifierVisitor().visit(ctx);
        String type = ctx.typeType().getText();
        System.out.println("type: " + type);
        System.out.println("decls : " + ctx.variableDeclarators().variableDeclarator().get(0).getText());
        System.out.println("field declaration " + ctx.getText());
        return super.visitFieldDeclaration(ctx);
    }
}
