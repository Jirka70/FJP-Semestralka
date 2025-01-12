package org.example.ast.clazz;

import org.example.ast.Variable;
import org.example.ast.expression.AbstractExpression;

import java.util.List;

public class FieldPrimitive extends Variable {

    public FieldPrimitive(List<String> modifiers, String type, String name, AbstractExpression expression) {
        super(modifiers, type, name, expression);
    }
}
