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
    LITERAL, EMPTY, AND("&&"), OR("||"), NOT_EQUAL_TO("!="), PLUS_EQUALS("+="),
        MINUS_EQUALS("-="), MULTIPLY_EQUALS("*="), DIVIDE_EQUALS("/="),
        MODULO_EQUALS("%="), ARRAY_CREATION, INDEX;

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

    public boolean isInequalityType() {
        return this == LESS_THAN
                || this == GREATER_THAN
                || this == LESS_THAN_OR_EQUAL
                || this == GREATER_THAN_OR_EQUAL;
    }

    public boolean isLogicalType() {
        return this == AND || this == OR;
    }

    public boolean isAssignmentType() {
        return this == ASSIGN
                || this == PRE_INC
                || this == PRE_DEC
                || this == POST_INC
                || this == POST_DEC
                || this == PLUS_EQUALS
                || this == MINUS_EQUALS
                || this == MULTIPLY_EQUALS
                || this == DIVIDE_EQUALS
                || this == MODULO_EQUALS;
    }
}
