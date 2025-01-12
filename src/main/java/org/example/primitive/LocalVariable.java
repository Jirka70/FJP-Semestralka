package org.example.primitive;

import org.example.primitive.expression.AbstractExpression;

import java.util.List;

public class LocalVariable extends Variable {

    public LocalVariable(List<String> modifiers, String type, String name, AbstractExpression expression) {
        super(modifiers, type, name, expression);
    }
}
