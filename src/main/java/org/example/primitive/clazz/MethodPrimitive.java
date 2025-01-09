package org.example.primitive.clazz;

import org.example.primitive.ParameterPrimitive;

import java.util.ArrayList;
import java.util.List;

public class MethodPrimitive {
    public final String mDeclaredReturnType;
    public final String mName;
    public final List<ParameterPrimitive> mParameters = new ArrayList<>();

    public MethodPrimitive(String returnType, String name, List<ParameterPrimitive> parameters) {
        mDeclaredReturnType = returnType;
        mName = name;
        if (parameters != null)
            mParameters.addAll(parameters);
    }

    @Override
    public String toString() {
        return mDeclaredReturnType + " " + mName + " " + mParameters;
    }
}
