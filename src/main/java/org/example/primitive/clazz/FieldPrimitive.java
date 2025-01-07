package org.example.primitive.clazz;

import org.example.primitive.Variable;
import org.example.primitive.expression.AbstractExpression;
import org.example.primitive.variable.Modifier;

public class FieldPrimitive extends Variable {

    public FieldPrimitive(Modifier modifier, String name, AbstractExpression expression) {
        super(modifier, name, expression);
    }
}
