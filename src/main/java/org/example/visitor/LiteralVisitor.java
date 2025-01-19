package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.expression.LiteralExpression;
import org.example.util.Location;

public class LiteralVisitor extends IavaParserBaseVisitor<LiteralExpression> {

    @Override
    public LiteralExpression visitCharLit(IavaParser.CharLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.CHAR_LITERAL, location);
    }

    @Override
    public LiteralExpression visitStringLit(IavaParser.StringLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.STRING_LITERAL, location);
    }

    @Override
    public LiteralExpression visitBoolLit(IavaParser.BoolLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.BOOL_LITERAL, location);
    }

    @Override
    public LiteralExpression visitNullLit(IavaParser.NullLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.NULL_LITERAL, location);
    }

    @Override
    public LiteralExpression visitDecimalLit(IavaParser.DecimalLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.DECIMAL_LITERAL, location);
    }

    @Override
    public LiteralExpression visitHexLit(IavaParser.HexLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.HEX_LITERAL, location);
    }

    @Override
    public LiteralExpression visitOctLit(IavaParser.OctLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.OCT_LITERAL, location);
    }

    @Override
    public LiteralExpression visitBinaryLit(IavaParser.BinaryLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.BINARY_LITERAL, location);
    }

    @Override
    public LiteralExpression visitFloatLit(IavaParser.FloatLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.FLOAT_LITERAL, location);
    }

    @Override
    public LiteralExpression visitHexFloatLit(IavaParser.HexFloatLitContext ctx) {
        Location location = new Location(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new LiteralExpression(ctx.getText(), LiteralExpression.LiteralType.HEX_FLOAT_LITERAL, location);
    }
}
