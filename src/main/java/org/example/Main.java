package org.example;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.errorHandle.errorListener.IavaSyntaxErrorListener;
import org.example.visitor.AppVisitor;

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
        parser.addErrorListener(new IavaSyntaxErrorListener());
        ParseTree tree = parser.compilationUnit(); // start parsing at the 'compilationUnit' rule and create parse tree
        AppVisitor visitor = new AppVisitor();
        visitor.visit(tree);


    }
}