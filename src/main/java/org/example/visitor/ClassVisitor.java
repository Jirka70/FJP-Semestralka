package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.clazz.ClassPrimitive;
import org.example.primitive.clazz.FieldPrimitive;
import org.example.primitive.clazz.method.MethodPrimitive;

import java.util.ArrayList;
import java.util.List;

// TODO generate ClassPrimitive instance
// TODO many methods are here just "testing", how ANTLR API works
public class ClassVisitor extends IavaParserBaseVisitor<ClassPrimitive> {

    @Override
    public ClassPrimitive visitClassDeclaration(IavaParser.ClassDeclarationContext ctx) {
        List<FieldPrimitive> fields = new ArrayList<>();
        List<MethodPrimitive> methods = new ArrayList<>();
        List<ClassPrimitive> classes = new ArrayList<>();

        for (IavaParser.ClassBodyDeclarationContext cbCtx : ctx.classBody().classBodyDeclaration()) {
            IavaParser.MemberDeclarationContext memberCtx = cbCtx.memberDeclaration();
            if (memberCtx != null) {
                if (memberCtx.methodDeclaration() != null) {
                    methods.add(new MethodVisitor().visit(memberCtx.methodDeclaration()));
                } else if (memberCtx.fieldDeclaration() != null) {
                    fields.addAll(new FieldVisitor().visit(memberCtx.fieldDeclaration()));
                } else if (memberCtx.classDeclaration() != null) {
                    classes.add(visit(memberCtx.classDeclaration()));
                }
            }
        }

        //System.out.println("Class: " + new ClassPrimitive(fields, methods, classes, ctx.identifier().getText()));
        return new ClassPrimitive(fields, methods, classes, ctx.identifier().getText());
    }

    @Override
    public ClassPrimitive visitVariableModifier(IavaParser.VariableModifierContext ctx) {
        return super.visitVariableModifier(ctx);
    }

    @Override
    public ClassPrimitive visitFieldDeclaration(IavaParser.FieldDeclarationContext ctx) {
        return super.visitFieldDeclaration(ctx);
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
