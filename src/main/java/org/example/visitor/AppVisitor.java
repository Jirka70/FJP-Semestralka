package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.AppPrimitive;

public class AppVisitor extends IavaParserBaseVisitor<AppPrimitive> {

    @Override
    public AppPrimitive visitClassDeclaration(IavaParser.ClassDeclarationContext ctx) {
        return super.visitClassDeclaration(ctx);
    }

    @Override
    public AppPrimitive visitCompilationUnit(IavaParser.CompilationUnitContext ctx) {
        System.out.println(ctx.getText());
        return super.visitCompilationUnit(ctx);
    }
}
