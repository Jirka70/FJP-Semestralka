package org.example.ast.clazz;

import org.example.ast.clazz.method.MethodPrimitive;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.ClassAlreadyDefinedException;
import org.example.semantic.exception.symbolTableException.UndefinedClassException;
import org.example.semantic.exception.symbolTableException.UndefinedMethodException;
import org.example.semantic.exception.symbolTableException.UnsupportedNameException;
import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.ClassDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.ClassScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.ClassSymbol;
import org.example.semantic.symbolTable.symbol.MethodSymbol;
import org.example.semantic.type.AbstractType;
import org.example.util.Location;

import java.util.ArrayList;
import java.util.List;

public class ClassPrimitive implements ISemanticallyAnalyzable {
    public final List<FieldPrimitive> mFields = new ArrayList<>();
    public final List<MethodPrimitive> mMethods = new ArrayList<>();
    public final List<ClassPrimitive> mClasses = new ArrayList<>();
    public final Location mLocation;

    public final String mName;

    public ClassPrimitive(List<FieldPrimitive> fields, List<MethodPrimitive> methods,
                          List<ClassPrimitive> classes, String name, Location location) {
        if (fields != null)
            mFields.addAll(fields);
        if (methods != null)
            mMethods.addAll(methods);
        if (classes != null)
            mClasses.addAll(classes);
        mName = name;
        mLocation = location;
    }

    @Override
    public String toString() {
        return "name: " + mName + ", fields: " + mFields + ", methods: " + mMethods + ", classes: " + mClasses;
    }

    @Override
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        for (FieldPrimitive field : mFields) {
            field.analyze(abstractScope);
        }

        for (MethodPrimitive method : mMethods) {
            List<AbstractType> parameterTypes = method.mParameters.collectParameterTypesFromMethod();
            AbstractSymbol methodSymbol = new MethodSymbol(method.mName, parameterTypes);
            AbstractScope methodAbstractScope = abstractScope.getChildScopeBySymbol(methodSymbol);
            if (methodAbstractScope == null) {
                throw new UndefinedMethodException("Method " + method.mName + " is not defined");
            }

            method.analyze(methodAbstractScope);
        }

        for (ClassPrimitive classPrimitive : mClasses) {
            AbstractSymbol classSymbol = new ClassSymbol(classPrimitive.mName);
            AbstractScope classAbstractScope = abstractScope.getChildScopeBySymbol(classSymbol);
            if (classAbstractScope == null) {
                throw new UndefinedClassException("Class " + classPrimitive.mName + " is not defined");
            }

            classPrimitive.analyze(classAbstractScope);
        }
    }


    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        AbstractDescriptor classDescriptor = new ClassDescriptor(mName);
        AbstractScope classAbstractScope = new ClassScope(currentAbstractScope, classDescriptor);
        AbstractSymbol classSymbol = new ClassSymbol(mName);

        if (currentAbstractScope.isTypeDefined(classSymbol)) {
            throw new UnsupportedNameException("Class name " + mName + " cannot be used as name, because it is a type");
        }

        currentAbstractScope.addChildScope(classSymbol, classAbstractScope);


        if (currentAbstractScope.isSymbolDefinedOnLocation(classSymbol, mLocation)) {
            AbstractDescriptor descriptor = currentAbstractScope.getSymbolDescriptorOnLocation(classSymbol, mLocation);
            if (descriptor.equals(classDescriptor)) {
                throw new ClassAlreadyDefinedException("Class with name \"" + mName + "\" already exists");
            }
        }

        classAbstractScope.addSymbol(classSymbol, classDescriptor, mLocation);

        for (FieldPrimitive field : mFields) {
            field.collectData(classAbstractScope);
        }

        for (MethodPrimitive method : mMethods) {
            method.collectData(classAbstractScope);
        }

        for (ClassPrimitive classPrimitive : mClasses) {
            classPrimitive.collectData(classAbstractScope);
        }
    }
}
