package org.example.primitive.clazz.method;

import org.example.primitive.ParameterPrimitive;

import java.util.ArrayList;
import java.util.List;

public class MethodPrimitive {
    public final String mDeclaredReturnType;
    public final String mName;
    public final List<ParameterPrimitive> mParameters = new ArrayList<>();
    public final MethodBody mMethodBody;

    public MethodPrimitive(String returnType, String name, List<ParameterPrimitive> parameters, MethodBody methodBody) {
        mDeclaredReturnType = returnType;
        mName = name;
        if (parameters != null)
            mParameters.addAll(parameters);
        mMethodBody = methodBody;
    }

    @Override
    public String toString() {
        return mDeclaredReturnType + " " + mName + " " + mParameters + " " + mMethodBody;
    }
}
