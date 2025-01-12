package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.AST;
import org.example.ast.clazz.ClassPrimitive;

import java.util.ArrayList;
import java.util.List;

public class AppVisitor extends IavaParserBaseVisitor<AST> {

    @Override
    public AST visitCompilationUnit(IavaParser.CompilationUnitContext ctx) {
        List<ClassPrimitive> classes = new ArrayList<>();
        for (IavaParser.TypeDeclarationContext tdCtx : ctx.typeDeclaration()) {
            ClassPrimitive classPrimitive = new ClassVisitor().visit(tdCtx);
            classes.add(classPrimitive);
        }

        //System.out.println("App: " + new AppPrimitive(classes));
        return new AST(classes);
    }
}
