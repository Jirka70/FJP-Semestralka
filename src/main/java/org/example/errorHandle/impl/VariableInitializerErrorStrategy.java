package org.example.errorHandle.impl;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.example.IavaParser;
import org.example.errorHandle.ErrorHandleStrategy;

public class VariableInitializerErrorStrategy implements ErrorHandleStrategy {
    @Override
    public boolean canHandleError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                                  int charPositionInLine, String msg, RecognitionException e) {
        if (e == null) return false;
        return e.getCtx() instanceof IavaParser.VariableInitializerContext;
    }

    @Override
    public void handleError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        System.err.println("Expected variable initializer on line: " + line + ":" + charPositionInLine);
    }
}
