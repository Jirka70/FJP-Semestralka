package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.clazz.ClassPrimitive;
import org.example.ast.clazz.FieldPrimitive;
import org.example.ast.clazz.method.MethodPrimitive;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

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

        Location location = getMethodLocation(ctx);
        return new ClassPrimitive(fields, methods, classes, ctx.identifier().getText(), location);
    }

    private Location getMethodLocation(IavaParser.ClassDeclarationContext ctx) {
        return new Location(ctx.start.getLine(),
                ctx.start.getCharPositionInLine());
    }
}
