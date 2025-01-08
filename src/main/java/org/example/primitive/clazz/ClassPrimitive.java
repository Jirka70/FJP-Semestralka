package org.example.primitive.clazz;

import java.util.ArrayList;
import java.util.List;

public class ClassPrimitive {
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
}
