package org.example.ast.expression;


public class EmptyExpression extends AbstractExpression {

    public EmptyExpression() {
        super(ExpressionType.EMPTY);
    }

    @Override
    public String toString() {
        return "EmptyExpression";
    }
}
