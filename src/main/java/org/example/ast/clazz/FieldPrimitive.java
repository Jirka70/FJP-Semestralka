package org.example.ast.clazz;

import org.example.ast.Variable;
import org.example.ast.expression.AbstractExpression;
import org.example.util.Location;

import java.util.List;

public class FieldPrimitive extends Variable {

    public FieldPrimitive(List<String> modifiers, String type, String name, AbstractExpression expression,
                          Location location) {
        super(modifiers, type, name, expression, location);
    }
}
