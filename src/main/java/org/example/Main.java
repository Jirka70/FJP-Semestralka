package org.example;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {
        /* TODO: kontrola argumentů... */
        String inputName = args[0];
        CharStream input = null; // vytvoření character streamu
        try {
            input = CharStreams.fromFileName(inputName);
        } catch (Exception e) {
            System.out.println("Chyba pri otevirani souboru: " + e);
        }

        IavaLexer lexer = new IavaLexer(input); // vytvoření lexeru
        CommonTokenStream tokens = new CommonTokenStream(lexer); // vytvoření token streamu
        IavaParser parser = new IavaParser(tokens); // vytvoření parseru
        ParseTree tree = parser.compilationUnit(); // start parsing at the 'compilationUnit' rule and create parse tree


        /* TODO: implementovat Visitory rozšiřující IavaParserBaseVisitor<T> a překrývající vybrané visitxxx metody.
              Pomocí nich projít tree a vytvořit reprezentaci programu v paměti. Z té pak vygenerovat instrukce */
        System.out.println(tree.toStringTree(parser));

    }
}