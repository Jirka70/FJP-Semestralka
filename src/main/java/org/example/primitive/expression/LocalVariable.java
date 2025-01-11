package org.example.primitive.expression;

import org.example.primitive.Variable;

import java.util.List;

public class LocalVariable extends Variable {

    public LocalVariable(List<String> modifiers, String type, String name, AbstractExpression expression) {
        super(modifiers, type, name, expression);
    }
}
