package org.example;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.ast.AST;
import org.example.codeGeneration.CodeGenerator;
import org.example.errorHandle.errorListener.IavaSyntaxErrorListener;
import org.example.semantic.SemanticAnalyzer;
import org.example.semantic.exception.SemanticException;
import org.example.visitor.AppVisitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Main {
    private static final int EXPECTED_ARGS_COUNT = 2;
    private static final char BREAK_LINE = '\n';

    public static void main(String[] args) throws SemanticException {
        if (args.length != EXPECTED_ARGS_COUNT) {
            System.out.println("Expected "
                    + EXPECTED_ARGS_COUNT
                    + " arguments (path to .iava source code and output file with PL/0 instructions)");
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
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(ast);
        try {

            semanticAnalyzer.analyse();
        } catch (SemanticException e) {
            e.printStackTrace();
            return;
        }

        CodeGenerator generator = new CodeGenerator(ast);

        String outputFile = args[1];
        List<String> pl0instructions = generator.generate(semanticAnalyzer.mSymbolTable);
        writeInstructionsToOutputFile(pl0instructions, outputFile);
    }

    private static void writeInstructionsToOutputFile(List<String> instructions, String outputFile) {
        try (Writer writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String instruction : instructions) {
                writeInstruction(writer, instruction);
            }
        } catch (IOException e) {
            System.err.println("Cannot write instructions to output file " + outputFile + ". Exception: " + e);
        }
    }

    private static void writeInstruction(Writer writer, String instruction) throws IOException {
        writer.write(instruction);
        writer.write(BREAK_LINE);
    }
}
