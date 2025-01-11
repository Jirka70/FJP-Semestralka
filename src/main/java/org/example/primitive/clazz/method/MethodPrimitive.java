package org.example.primitive.clazz.method;

import org.example.primitive.ParameterPrimitive;
import org.example.statement.Block;

import java.util.ArrayList;
import java.util.List;

public class MethodPrimitive {
    public final String mDeclaredReturnType;
    public final String mName;
    public final List<ParameterPrimitive> mParameters = new ArrayList<>();
    public final Block mMethodBody;

    public MethodPrimitive(String returnType, String name, List<ParameterPrimitive> parameters, Block methodBody) {
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
