package org.example.ast;

import org.example.ast.clazz.ClassPrimitive;
import org.example.codeGeneration.CodeGenerator;
import org.example.codeGeneration.IGeneratable;
import org.example.semantic.ISemanticallyAnalyzable;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.UndefinedClassException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.symbol.AbstractSymbol;
import org.example.semantic.symbolTable.symbol.ClassSymbol;

import java.util.ArrayList;
import java.util.List;

public class AST implements ISemanticallyAnalyzable, IGeneratable {
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
    public void analyze(AbstractScope abstractScope) throws SemanticException {
        for (ClassPrimitive classPrimitive : mClasses) {
            AbstractSymbol classPrimitiveSymbol = new ClassSymbol(classPrimitive.mName);
            AbstractScope classPrimitiveAbstractScope = abstractScope.getChildScopeBySymbol(classPrimitiveSymbol);
            if (classPrimitiveAbstractScope == null) {
                throw new UndefinedClassException("Class " + classPrimitive.mName + " is not defined");
            }

            classPrimitive.analyze(classPrimitiveAbstractScope);
        }
    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) throws SemanticException {
        for (ClassPrimitive classPrimitive : mClasses) {
            classPrimitive.collectData(currentAbstractScope);
        }
    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        System.out.println("Generating AST");
        generator.addInstruction("JMP 0 1");
        for (ClassPrimitive classPrimitive : mClasses)
            classPrimitive.generate(currentAbstractScope, generator);
    }
}
