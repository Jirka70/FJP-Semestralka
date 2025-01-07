package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.clazz.ClassPrimitive;
import org.example.primitive.expression.AbstractExpression;
import org.example.primitive.variable.Modifier;

// TODO generate ClassPrimitive instance
// TODO many methods are here just "testing", how ANTLR API works
public class ClassVisitor extends IavaParserBaseVisitor<ClassPrimitive> {

    @Override
    public ClassPrimitive visitClassDeclaration(IavaParser.ClassDeclarationContext ctx) {
        System.out.println("class declaration " + ctx.getText());
        System.out.println("class: " + ctx.identifier().getText());
        for (IavaParser.ClassBodyDeclarationContext decl : ctx.classBody().classBodyDeclaration()) {
            System.out.println("class body: " + decl.getText());
        }
        return super.visitClassDeclaration(ctx);
    }

    @Override
    public ClassPrimitive visitVariableModifier(IavaParser.VariableModifierContext ctx) {
        return super.visitVariableModifier(ctx);
    }

    @Override
    public ClassPrimitive visitFieldDeclaration(IavaParser.FieldDeclarationContext ctx) {
        AbstractExpression expression = new ExpressionVisitor().visit(ctx);
        Modifier modifier = new ModifierVisitor().visit(ctx);
        String type = ctx.typeType().getText();
        String name = new VariableNameVisitor().visit(ctx);
        System.out.println("Modifier: " + (modifier == null ? null : modifier.mModifier));
        System.out.println("type: " + type);
        System.out.println("Field name: " + name);
        System.out.println("expression: " + expression);
        return super.visitFieldDeclaration(ctx);
    }

    // TODO make separate class for creating IF, WHILE and SWITCH statements.
    // TODO Each statement could be recognized thanks to the KEYWORD method (e. g. IF() returns non null value, whether statement is "if statement")
    @Override
    public ClassPrimitive visitStatement(IavaParser.StatementContext ctx) {
        System.out.println("statement: " + ctx.IF());
        return super.visitStatement(ctx);
    }

    @Override
    public ClassPrimitive visitVariableDeclarator(IavaParser.VariableDeclaratorContext ctx) {
        return super.visitVariableDeclarator(ctx);
    }

    @Override
    public ClassPrimitive visitVariableInitializer(IavaParser.VariableInitializerContext ctx) {
        /*System.out.println("variable initializer " + ctx.getText());

        AbstractExpression variableExpression = new ExpressionVisitor().visit(ctx.expression());
        System.out.println("Expression: " + ctx.expression().getText());*/

        return super.visitVariableInitializer(ctx);
    }

    @Override
    public ClassPrimitive visitClassOrInterfaceModifier(IavaParser.ClassOrInterfaceModifierContext ctx) {
        //System.out.println("class modifier: " + ctx.getText());
        return super.visitClassOrInterfaceModifier(ctx);
    }

    @Override
    public ClassPrimitive visitClassOrInterfaceType(IavaParser.ClassOrInterfaceTypeContext ctx) {
        //System.out.println("class type: " + ctx.getText());
        return super.visitClassOrInterfaceType(ctx);
    }

    @Override
    public ClassPrimitive visitClassBodyDeclaration(IavaParser.ClassBodyDeclarationContext ctx) {
        //System.out.println("class body declaration: " + ctx.getText());
        return super.visitClassBodyDeclaration(ctx);
    }

    @Override
    public ClassPrimitive visitClassBody(IavaParser.ClassBodyContext ctx) {
        //System.out.println("class body: " + ctx.getText());
        return super.visitClassBody(ctx);
    }

    @Override
    public ClassPrimitive visitClassCreatorRest(IavaParser.ClassCreatorRestContext ctx) {
        System.out.println("class creator rest: " + ctx.getText());
        return super.visitClassCreatorRest(ctx);
    }
}
