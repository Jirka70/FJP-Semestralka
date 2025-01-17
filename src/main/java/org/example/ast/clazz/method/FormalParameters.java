package org.example.ast.clazz.method;

import org.example.semantic.type.AbstractType;
import org.example.semantic.type.TypeFactory;

import java.util.ArrayList;
import java.util.List;

public class FormalParameters {
    public final List<ParameterPrimitive> mParameters = new ArrayList<>();

    public FormalParameters(List<ParameterPrimitive> parameters) {
        if (parameters != null) {
            mParameters.addAll(parameters);
        }
    }

    public List<AbstractType> collectParameterTypesFromMethod() {
        List<AbstractType> parameterTypes = new ArrayList<>();

        for (ParameterPrimitive arg : mParameters) {
            AbstractType type = getAbstractTypeFromParameterPrimitive(arg);
            parameterTypes.add(type);
        }

        return parameterTypes;
    }

    private AbstractType getAbstractTypeFromParameterPrimitive(ParameterPrimitive parameterPrimitive) {
        return TypeFactory.fromString(parameterPrimitive.mDeclaredType);
    }
}
