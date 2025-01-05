package org.example;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.primitive.expression.AbstractExpression;
import org.example.visitor.AppVisitor;
import org.example.visitor.ExpressionVisitor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Expected 1 argument (path to .iava source code)");
            return;
        }
        String inputName = args[0];
        CharStream input = null; // vytvoření character streamu
        try {
            input = CharStreams.fromFileName(inputName);
        } catch (Exception e) {
            System.out.println("Chyba pri otevirani souboru: " + e);
        }

        if (input == null) {
            System.out.println("File " + inputName + " was not found");
            return;
        }


        IavaLexer lexer = new IavaLexer(input); // vytvoření lexeru
        CommonTokenStream tokens = new CommonTokenStream(lexer); // vytvoření token streamu
        IavaParser parser = new IavaParser(tokens); // vytvoření parseru
        ParseTree tree = parser.compilationUnit(); // start parsing at the 'compilationUnit' rule and create parse tree
        AppVisitor visitor = new AppVisitor();

        visitor.visit(tree);

        /* TODO: implementovat Visitory rozšiřující IavaParserBaseVisitor<T> a překrývající vybrané visitxxx metody.
              Pomocí nich projít tree a vytvořit reprezentaci programu v paměti. Z té pak vygenerovat instrukce */

        //System.out.println(tree.toStringTree(parser));

    }

    static class CustomIavaVisitor extends IavaParserBaseVisitor<String> {



        @Override
        public String visitBlock(IavaParser.BlockContext ctx) {
            return super.visitBlock(ctx);
        }



        @Override
        public String visitExpressionList(IavaParser.ExpressionListContext ctx) {
            System.out.println("expressionList: " + ctx.getText());
            List<IavaParser.ExpressionContext> expressionContextList = ctx.expression();

            for (IavaParser.ExpressionContext expressionContext : expressionContextList) {
                visit(expressionContext);
            }

            return super.visitExpressionList(ctx);
        }



        @Override
        public String visitExpression(IavaParser.ExpressionContext ctx) {
            System.out.println("expression: " + ctx.getText());
            if (ctx.bop != null) System.out.println("operator: " + ctx.bop.getText());
            List<IavaParser.ExpressionContext> expressionContextList = ctx.expression();

            for (IavaParser.ExpressionContext expressionContext : expressionContextList) {
                visit(expressionContext);
            }

            return super.visitExpression(ctx);
        }

        @Override
        public String visitForControl(IavaParser.ForControlContext ctx) {
            System.out.println("for init: " + ctx.forInit().getText());
            IavaParser.ExpressionListContext forUpdate = ctx.forUpdate;

            if (ctx.expression() != null) {
                System.out.println(ctx.expression().getText());
            }






            System.out.println("for update: " + forUpdate.getText());
            return super.visitForControl(ctx);
        }

        /*@Override
        public String visitTypeDeclaration(IavaParser.TypeDeclarationContext ctx) {
            System.out.println("class: " +  ctx.classDeclaration().getText());
            return super.visitTypeDeclaration(ctx);
        }

        @Override
        public String visitVariableDeclarator(IavaParser.VariableDeclaratorContext ctx) {
            String variableName = ctx.variableDeclaratorId().getText();
            System.out.println("variable name: " + variableName);
            return super.visitVariableDeclarator(ctx);
        }

        @Override
        public String visitExpression(IavaParser.ExpressionContext ctx) {
            System.out.println("expression: " + ctx.getText());
            return super.visitExpression(ctx);
        }

        @Override
        public String visitVariableModifier(IavaParser.VariableModifierContext ctx) {
            System.out.println("Variable Modifier: " + ctx.getText());
            return super.visitVariableModifier(ctx);
        }

        @Override
        public String visitMethodDeclaration(IavaParser.MethodDeclarationContext ctx) {
            System.out.println("Return type: " + ctx.typeTypeOrVoid().getText());
            return super.visitMethodDeclaration(ctx);
        }

        @Override
        public String visitTypeTypeOrVoid(IavaParser.TypeTypeOrVoidContext ctx) {
            if (ctx.typeType() != null) System.out.println("vubec nevim: " + ctx.typeType().getText());
            return super.visitTypeTypeOrVoid(ctx);
        }

        @Override
        public String visitLiteral(IavaParser.LiteralContext ctx) {
            System.out.println("literal: " + ctx.getText());
            return super.visitLiteral(ctx);
        }

        @Override
        public String visitLocalVariableDeclaration(IavaParser.LocalVariableDeclarationContext ctx) {
            System.out.println("variable: " + ctx.variableDeclarators().getText());
            System.out.println("type: " + ctx.typeType().getText());
            return super.visitLocalVariableDeclaration(ctx);
        }

        @Override
        public String visitVariableInitializer(IavaParser.VariableInitializerContext ctx) {
            return super.visitVariableInitializer(ctx);
        }*/
    }
}