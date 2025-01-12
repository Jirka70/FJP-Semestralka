package org.example.visitor;

import org.antlr.v4.runtime.RuleContext;
import org.example.IavaParser;
import org.example.IavaParserBaseVisitor;
import org.example.ast.clazz.method.ParameterPrimitive;
import org.example.ast.clazz.method.MethodPrimitive;
import org.example.ast.statement.Block;

import java.util.ArrayList;
import java.util.List;

public class MethodVisitor extends IavaParserBaseVisitor<MethodPrimitive> {

    @Override
    public MethodPrimitive visitMethodDeclaration(IavaParser.MethodDeclarationContext ctx) {
        String returnType = ctx.typeTypeOrVoid().getText() + "[]".repeat(ctx.LBRACK().size());
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
                //System.out.println("Parameter: " + parameter);
            }
        }

        Block methodBody = new MethodBodyVisitor().visit(ctx.methodBody());

        //System.out.println("Method: " + methodPrimitive);
        //System.out.println("Method body: " + methodBody);
        return new MethodPrimitive(returnType, name, parameters, methodBody);
    }
}