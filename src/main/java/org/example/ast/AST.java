package org.example.ast;

import org.example.ast.clazz.ClassPrimitive;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.AppDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class AST implements ISemanticallyAnalyzable {
    public final List<ClassPrimitive> mClasses = new ArrayList<>();

    public AST(List<ClassPrimitive> classes) {
        if (classes != null)
            mClasses.addAll(classes);
    }

    @Override
    public String toString() {
        return mClasses.toString();
    }

    @Override
    public void analyze(SymbolTable symbolTable) {
        for (ClassPrimitive classPrimitive : mClasses) {
            classPrimitive.analyze(symbolTable);
        }
    }

    @Override
    public void collectData(Scope currentScope) {
        Scope rootScope = createRootScope();
        for (ClassPrimitive classPrimitive : mClasses) {
            classPrimitive.collectData(rootScope);
        }
    }

    private Scope createRootScope() {
        AbstractDescriptor appDescriptor = new AppDescriptor();
        return new Scope(null, appDescriptor);
    }
}
