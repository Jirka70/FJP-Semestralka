package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.expression.AbstractExpression;
import org.example.statement.LocalVariable;

import java.util.ArrayList;
import java.util.List;

public class LocalVariableDeclarationVisitor extends IavaParserBaseVisitor<LocalVariable> {
    @Override
    public LocalVariable visitLocalVariableDeclaration(IavaParser.LocalVariableDeclarationContext ctx) {
        String type = extractType(ctx);
        List<String> modifiers = extractModifiers(ctx);
        String name = extractName(ctx);
        AbstractExpression expression = extractExpression(ctx);

        return new LocalVariable(modifiers, type, name, expression);
    }

    private List<String> extractModifiers(IavaParser.LocalVariableDeclarationContext ctx) {
        List<String> modifiers = new ArrayList<>();
        List<IavaParser.VariableModifierContext> modifierCtxList = ctx.variableModifier();

        for (IavaParser.VariableModifierContext modifierCtx : modifierCtxList) {
            modifiers.add(modifierCtx.getText());
        }

        return modifiers;
    }

    private String extractType(IavaParser.LocalVariableDeclarationContext ctx) {
        return ctx.typeType().getText();
    }

    private String extractName(IavaParser.LocalVariableDeclarationContext ctx) {
        IavaParser.VariableDeclaratorsContext declaratorsContext = ctx.variableDeclarators();

        List<IavaParser.VariableDeclaratorContext> variableDeclaratorsContextList
                = declaratorsContext.variableDeclarator();

        for (IavaParser.VariableDeclaratorContext variableDeclaratorCtx : variableDeclaratorsContextList) {
            return variableDeclaratorCtx.variableDeclaratorId().getText();
        }

        return null;
    }

    private AbstractExpression extractExpression(IavaParser.LocalVariableDeclarationContext ctx) {
        IavaParser.VariableDeclaratorsContext declaratorsContext = ctx.variableDeclarators();

        List<IavaParser.VariableDeclaratorContext> variableDeclaratorsContextList
                = declaratorsContext.variableDeclarator();

        for (IavaParser.VariableDeclaratorContext variableDeclaratorCtx : variableDeclaratorsContextList) {
            return new ExpressionVisitor().visit(variableDeclaratorCtx.variableInitializer().expression());
        }

        return null;
    }
}
