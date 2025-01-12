package org.example.ast;

import org.example.ast.expression.AbstractExpression;

import java.util.List;

public class LocalVariable extends Variable {

    public LocalVariable(List<String> modifiers, String type, String name, AbstractExpression expression) {
        super(modifiers, type, name, expression);
    }
}
