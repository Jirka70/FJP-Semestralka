package org.example.primitive;

import org.example.primitive.clazz.ClassPrimitive;

import java.util.ArrayList;
import java.util.List;

public class AppPrimitive {
    public final List<ClassPrimitive> mClasses = new ArrayList<>();

    public AppPrimitive(List<ClassPrimitive> classes) {
        if (classes != null)
            mClasses.addAll(classes);
    }

    @Override
    public String toString() {
        return mClasses.toString();
    }
}
