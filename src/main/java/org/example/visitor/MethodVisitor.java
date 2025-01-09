package org.example.visitor;

import org.antlr.v4.runtime.RuleContext;
import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.primitive.ParameterPrimitive;
import org.example.primitive.clazz.MethodPrimitive;

import java.util.ArrayList;
import java.util.List;

public class MethodVisitor extends IavaParserBaseVisitor<MethodPrimitive> {

    @Override
    public MethodPrimitive visitMethodDeclaration(IavaParser.MethodDeclarationContext ctx) {
        StringBuilder returnType = new StringBuilder(ctx.typeTypeOrVoid().getText());
        returnType.append("[]".repeat(ctx.LBRACK().size()));
        String name = ctx.identifier().getText();

        List<ParameterPrimitive> parameters = new ArrayList<>();
        boolean hasParameters = (ctx.formalParameters().formalParameterList() != null);
        if (hasParameters) {
            var pCtxList = ctx.formalParameters().formalParameterList().formalParameter();
            for (var pCtx : pCtxList) {
                List<String> pModifiers = pCtx.variableModifier().stream().map(RuleContext::getText).toList();
                String pType = pCtx.typeType().getText();
                String pName = pCtx.variableDeclaratorId().getText();

                ParameterPrimitive parameter = new ParameterPrimitive(pModifiers, pType, pName);
                parameters.add(parameter);
                System.out.println("Parameter: " + parameter);
            }
        }

        // TODO: create Block representing method body
        // Block has a list of BlockStatements - either LocalVariableDeclarations or Statements

        System.out.println("Method: " + new MethodPrimitive(returnType.toString(), name, parameters));
        return new MethodPrimitive(returnType.toString(), name, parameters);
    }
}
