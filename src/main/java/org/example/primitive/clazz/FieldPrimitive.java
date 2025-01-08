package org.example.primitive.clazz;

import org.example.primitive.Variable;
import org.example.primitive.expression.AbstractExpression;

import java.util.List;

public class FieldPrimitive extends Variable {

    public FieldPrimitive(List<String> modifiers, String type, String name, AbstractExpression expression) {
        super(modifiers, type, name, expression);
    }
}
