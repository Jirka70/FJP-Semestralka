package org.example.ast;

import org.example.ast.clazz.ClassPrimitive;

import java.util.ArrayList;
import java.util.List;

public class AST {
    public final List<ClassPrimitive> mClasses = new ArrayList<>();

    public AST(List<ClassPrimitive> classes) {
        if (classes != null)
            mClasses.addAll(classes);
    }

    @Override
    public String toString() {
        return mClasses.toString();
    }
}
