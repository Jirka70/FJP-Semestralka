package org.example.visitor;

import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.LocalVariable;
import org.example.ast.expression.AbstractExpression;
import org.example.ast.statement.LocalVariableDeclaration;

import java.util.ArrayList;
import java.util.List;

public class LocalVariableDeclarationVisitor extends IavaParserBaseVisitor<LocalVariableDeclaration> {
    @Override
    public LocalVariableDeclaration visitLocalVariableDeclaration(IavaParser.LocalVariableDeclarationContext ctx) {
        List<String> modifiers = extractModifiers(ctx);
        String type = extractType(ctx);
        List<LocalVariable> localVariables = extractLocalVariables(modifiers, type, ctx);

        return new LocalVariableDeclaration(localVariables);
    }

    private List<LocalVariable> extractLocalVariables(List<String> modifiers, String type,
                                                      IavaParser.LocalVariableDeclarationContext ctx) {
        List<LocalVariable> localVariables = new ArrayList<>();

        List<IavaParser.VariableDeclaratorContext> variableDeclaratorContextList = ctx
                .variableDeclarators()
                .variableDeclarator();

        for (IavaParser.VariableDeclaratorContext variableDeclaratorContext : variableDeclaratorContextList) {
            LocalVariable localVariable = extractLocalVariable(modifiers, type, variableDeclaratorContext);
            localVariables.add(localVariable);
        }

        return localVariables;
    }

    private LocalVariable extractLocalVariable(List<String> modifiers, String type,
                                               IavaParser.VariableDeclaratorContext variableDeclaratorContext) {
        String name = variableDeclaratorContext.variableDeclaratorId().getText();

        AbstractExpression expression = null;
        if (variableDeclaratorContext.variableInitializer() != null &&
                variableDeclaratorContext.variableInitializer().expression() != null) {
            IavaParser.ExpressionContext exprCtx = variableDeclaratorContext.variableInitializer().expression();
            expression = new ExpressionVisitor().visit(exprCtx);
        }

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
}
