package org.example.ast.expression;

import org.example.codeGeneration.CodeGenerator;
import org.example.semantic.exception.SemanticException;
import org.example.semantic.exception.symbolTableException.TypeMismatchException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.*;
import org.example.util.Location;

public class LiteralExpression extends PrimaryExpression {


    public enum LiteralType {
        CHAR_LITERAL,
        STRING_LITERAL,
        BOOL_LITERAL,
        NULL_LITERAL,
        DECIMAL_LITERAL,
        HEX_LITERAL,
        OCT_LITERAL,
        BINARY_LITERAL,
        FLOAT_LITERAL,
        HEX_FLOAT_LITERAL
    }

    public final String mLiteral;
    public final LiteralType mType;

    public LiteralExpression(String literal, LiteralType type, Location location) {
        super(ExpressionType.LITERAL, location);
        mLiteral = literal;
        mType = type;
    }

    @Override
    public AbstractType evaluateType(AbstractScope abstractScope) throws SemanticException {
        if (isIntegerLiteral()) {
            return new IntType();
        }

        if (isFloatLiteral()) {
            return new FloatType();
        }

        if (isBooleanLiteral()) {
            return new BooleanType();
        }

        if (isObjectLiteral()) {
            if (mType == LiteralType.STRING_LITERAL) {
                return new ObjectType("String");
            }

            return new NullType();
        }

        throw new TypeMismatchException("Type of literal " + mLiteral + " is not defined");
    }

    private boolean isObjectLiteral() {
        return mType == LiteralType.STRING_LITERAL
                || mType == LiteralType.NULL_LITERAL;
    }

    private boolean isBooleanLiteral() {
        return mType == LiteralType.BOOL_LITERAL;
    }

    private boolean isFloatLiteral() {
        return isIntegerLiteral()
                || mType == LiteralType.FLOAT_LITERAL
                || mType == LiteralType.HEX_FLOAT_LITERAL;
    }

    private boolean isIntegerLiteral() {
        return mType == LiteralType.CHAR_LITERAL
                || mType == LiteralType.DECIMAL_LITERAL
                || mType == LiteralType.HEX_LITERAL
                || mType == LiteralType.OCT_LITERAL
                || mType == LiteralType.BINARY_LITERAL;
    }

    @Override
    public String toString() {
        return mLiteral;
    }

    @Override
    public void analyze(AbstractScope abstractScope) {

    }

    @Override
    public void collectData(AbstractScope currentAbstractScope) {

    }

    @Override
    public void generate(AbstractScope currentAbstractScope, CodeGenerator generator) {
        switch (mType) {
            case CHAR_LITERAL -> generateAsChar(generator);
            case STRING_LITERAL -> throw new RuntimeException("Strings not supported yet");
            case BOOL_LITERAL -> generateAsBoolean(generator);
            case NULL_LITERAL -> throw new RuntimeException("Null literal not supported yet");
            case DECIMAL_LITERAL, HEX_LITERAL, OCT_LITERAL, BINARY_LITERAL -> generateAsInt(generator);
            case FLOAT_LITERAL, HEX_FLOAT_LITERAL -> generateAsFloat(generator);
        }
    }

    private char toChar() {
        return mLiteral.replace("'", "").charAt(0);
    }

    private boolean toBoolean() {
        return mLiteral.equals("verus");
    }

    private int toInt() {
        if (mType.equals(LiteralType.BINARY_LITERAL))
            return Integer.parseInt(mLiteral.replace("b", "").replace("B", ""), 2);
        return Integer.decode(mLiteral.replace("_", ""));
    }

    private float toFloat() {
        return Float.parseFloat(mLiteral.replace("_", ""));
    }

    private void generateAsChar(CodeGenerator generator) {
        generator.addInstruction("LIT 0 " + (int) toChar());
    }

    private void generateAsBoolean(CodeGenerator generator) {
        generator.addInstruction("LIT 0 " + (toBoolean() ? 1 : 0));
    }

    private void generateAsInt(CodeGenerator generator) {
        generator.addInstruction("LIT 0 " + toInt());
    }

    private void generateAsFloat(CodeGenerator generator) {
        float f = toFloat();
        int intPart = (int) f;
        final int MUL_FACTOR = 6;
        // zaokrouhlení pro lepší přesnost
        double diff = Math.round((f - intPart) * Math.pow(10, MUL_FACTOR)) / Math.pow(10, MUL_FACTOR);
        int fracPart = (int) (diff * Math.pow(10, MUL_FACTOR));

        generator.addInstruction("LIT 0 " + intPart);
        generator.addInstruction("LIT 0 " + fracPart);
        generator.addInstruction("ITR 0 0");
    }
}
