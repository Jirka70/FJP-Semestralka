package org.example.ast.expression;

public enum ExpressionType {
    UNARY_PLUS,
    UNARY_MINUS,
    TERNARY,
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVISION("/"),
    MODULO("%"),
    BOOL_EQUALS("=="),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_THAN_OR_EQUAL("<="),
    GREATER_THAN_OR_EQUAL(">="),
    VALUE,
    ASSIGN("="),
    CAST,
    PRE_INC,
    PRE_DEC,
    TILDE,
    NEG,
    POST_INC,
    POST_DEC,
    METHOD_CALL,
    PARENTHESES,
    IDENTIFIER,
    LITERAL, EMPTY, AND("&&"), OR("||"); // TODO: doplnit chybejici typy

    public final String mOperator;

    ExpressionType(String operator) {
        this.mOperator = operator;
    }

    ExpressionType() {
        this.mOperator = null;
    }

    public static ExpressionType valueOfByOperator(String operator) {
        for (ExpressionType expressionType : ExpressionType.values()) {
            if (operator.equalsIgnoreCase(expressionType.mOperator)) {
                return expressionType;
            }
        }
        throw new IllegalArgumentException("No enum constant with operator " + operator);
    }
}
