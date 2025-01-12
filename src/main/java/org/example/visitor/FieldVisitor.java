package org.example.visitor;

import org.antlr.v4.runtime.RuleContext;
import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.clazz.FieldPrimitive;
import org.example.ast.expression.AbstractExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor for fields in classes
 */
public class FieldVisitor extends IavaParserBaseVisitor<List<FieldPrimitive>> {

    @Override
    public List<FieldPrimitive> visitFieldDeclaration(IavaParser.FieldDeclarationContext ctx) {
        String type = ctx.typeType().getText();

        IavaParser.ClassBodyDeclarationContext cbCtx = (IavaParser.ClassBodyDeclarationContext) ctx.getParent().getParent();
        List<String> modifiers = cbCtx.modifier().stream().map(RuleContext::getText).toList();

        List<FieldPrimitive> fields = new ArrayList<>();
        for (IavaParser.VariableDeclaratorContext dclCtx : ctx.variableDeclarators().variableDeclarator()) {
            String name = dclCtx.variableDeclaratorId().getText();
            AbstractExpression initExpr = null;
            if (dclCtx.variableInitializer() != null && dclCtx.variableInitializer().expression() != null) {
                IavaParser.ExpressionContext exprCtx = dclCtx.variableInitializer().expression();
                initExpr = new ExpressionVisitor().visit(exprCtx);
            }

            FieldPrimitive field = new FieldPrimitive(modifiers, type, name, initExpr);
            fields.add(field);
            System.out.println("Field: " + field);
        }
        return fields;
    }
}
