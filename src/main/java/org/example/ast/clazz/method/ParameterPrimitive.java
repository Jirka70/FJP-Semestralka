package org.example.ast.clazz.method;

import org.example.ast.Variable;

import java.util.List;

public class ParameterPrimitive extends Variable {

    public ParameterPrimitive(List<String> modifiers, String type, String name) {
        super(modifiers, type, name, null);
    }
}
