package org.example;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.ast.AST;
import org.example.errorHandle.errorListener.IavaSyntaxErrorListener;
import org.example.semantic.SemanticAnalyzer;
import org.example.semantic.exception.SemanticException;
import org.example.visitor.AppVisitor;

public class Main {
    public static void main(String[] args) throws SemanticException {
        if (args.length != 1) {
            System.out.println("Expected 1 argument (path to .iava source code)");
            return;
        }
        String inputName = args[0];
        CharStream input = null;
        try {
            input = CharStreams.fromFileName(inputName);
        } catch (Exception e) {
            System.out.println("Chyba pri otevirani souboru: " + e);
        }

        if (input == null) {
            System.out.println("File " + inputName + " was not found");
            return;
        }


        IavaLexer lexer = new IavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        IavaParser parser = new IavaParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new IavaSyntaxErrorListener());

        ParseTree tree = parser.compilationUnit();
        AppVisitor visitor = new AppVisitor();
        AST ast = visitor.visit(tree); // TODO make semantic analyze
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(ast); // Final keyword in binary expression + in POSTepxression and preexprssion
        semanticAnalyzer.analyse();
        System.out.println("done");
    }
}
