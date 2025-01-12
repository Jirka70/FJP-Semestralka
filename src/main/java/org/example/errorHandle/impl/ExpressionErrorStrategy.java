package org.example.errorHandle.impl;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.example.IavaParser;
import org.example.errorHandle.ErrorHandleStrategy;

public class ExpressionErrorStrategy implements ErrorHandleStrategy {
    @Override
    public boolean canHandleError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                                  String msg, RecognitionException e) {
        if (e == null) return false;
        return e.getCtx() instanceof IavaParser.ExpressionContext;
    }

    @Override
    public void handleError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        Token offendingToken = e.getOffendingToken();
        String offendingTokenText = offendingToken == null
            ? ""
            :  offendingToken.getText();

        System.err.println("Expected expression on line "
                + line
                + ":"
                + charPositionInLine
                + ". Got: "
                + "\""
                + offendingTokenText
                + "\"");
    }
}
