package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.AppPrimitive;
import org.example.primitive.clazz.ClassPrimitive;

import java.util.ArrayList;
import java.util.List;

public class AppVisitor extends IavaParserBaseVisitor<AppPrimitive> {

    @Override
    public AppPrimitive visitCompilationUnit(IavaParser.CompilationUnitContext ctx) {
        List<ClassPrimitive> classes = new ArrayList<>();
        for (IavaParser.TypeDeclarationContext tdCtx : ctx.typeDeclaration()) {
            ClassPrimitive classPrimitive = new ClassVisitor().visit(tdCtx);
            classes.add(classPrimitive);
        }

        //System.out.println("App: " + new AppPrimitive(classes));
        return new AppPrimitive(classes);
    }
}
