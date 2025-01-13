package org.example.ast.clazz;

import org.example.ast.clazz.method.MethodPrimitive;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.ClassDescriptor;
import org.example.semantic.symbolTable.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class ClassPrimitive implements ISemanticallyAnalyzable {
    public final List<FieldPrimitive> mFields = new ArrayList<>();
    public final List<MethodPrimitive> mMethods = new ArrayList<>();
    public final List<ClassPrimitive> mClasses = new ArrayList<>();

    public final String mName;

    public ClassPrimitive(List<FieldPrimitive> fields, List<MethodPrimitive> methods,
                          List<ClassPrimitive> classes, String name) {
        if (fields != null)
            mFields.addAll(fields);
        if (methods != null)
            mMethods.addAll(methods);
        if (classes != null)
            mClasses.addAll(classes);
        mName = name;
    }

    @Override
    public String toString() {
        return "name: " + mName + ", fields: " + mFields + ", methods: " + mMethods + ", classes: " + mClasses;
    }

    @Override
    public void analyze(SymbolTable symbolTable) {

    }

    @Override
    public void collectData(Scope currentScope) {
        AbstractDescriptor classDescriptor = new ClassDescriptor(mName);
        Scope classScope = new Scope(currentScope, classDescriptor);
        currentScope.addChildScope(classScope);

        for (FieldPrimitive field : mFields) {
            field.collectData(classScope);
        }

        for (MethodPrimitive method : mMethods) {
            method.collectData(classScope);
        }

        for (ClassPrimitive classPrimitive : mClasses) {
            classPrimitive.collectData(classScope);
        }
    }
}
