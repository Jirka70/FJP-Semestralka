package org.example.errorHandle.impl;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.example.errorHandle.ErrorHandleStrategy;

public class NoViableAlternativeErrorStrategy implements ErrorHandleStrategy {
    @Override
    public boolean canHandleError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                                  String msg, RecognitionException e) {
        if (e == null) return false;
        return e instanceof NoViableAltException;
    }

    @Override
    public void handleError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        System.err.println("Not a statement " + line + ":" + charPositionInLine);

    }
}