package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.expression.AbstractExpression;
import org.example.statement.LocalVariable;
import org.example.statement.LocalVariableDeclaration;

import java.util.ArrayList;
import java.util.List;

public class LocalVariableDeclarationVisitor extends IavaParserBaseVisitor<LocalVariableDeclaration> {
    @Override
    public LocalVariableDeclaration visitLocalVariableDeclaration(IavaParser.LocalVariableDeclarationContext ctx) {
        List<String> modifiers = extractModifiers(ctx);
        String type = extractType(ctx);
        List<LocalVariable> localVariables = extractLocalVariables(ctx);

        return new LocalVariableDeclaration(modifiers, type, localVariables);
    }

    private List<LocalVariable> extractLocalVariables(IavaParser.LocalVariableDeclarationContext ctx) {
        List<LocalVariable> localVariables = new ArrayList<>();

        List<IavaParser.VariableDeclaratorContext> variableDeclaratorContextList = ctx
                .variableDeclarators()
                .variableDeclarator();

        for (IavaParser.VariableDeclaratorContext variableDeclaratorContext : variableDeclaratorContextList) {
            LocalVariable localVariable = extractLocalVariable(variableDeclaratorContext);
            localVariables.add(localVariable);
        }

        return localVariables;
    }

    private LocalVariable extractLocalVariable(IavaParser.VariableDeclaratorContext variableDeclaratorContext) {
        String name = variableDeclaratorContext.variableDeclaratorId().getText();

        IavaParser.ExpressionContext expressionContext = variableDeclaratorContext.variableInitializer().expression();
        AbstractExpression expression = new ExpressionVisitor().visit(expressionContext);

        return new LocalVariable(name, expression);
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
}
