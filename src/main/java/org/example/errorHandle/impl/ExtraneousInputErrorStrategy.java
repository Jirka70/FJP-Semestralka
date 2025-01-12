package org.example.errorHandle.impl;

import org.antlr.v4.runtime.*;
import org.example.errorHandle.ErrorHandleStrategy;

public class ExtraneousInputErrorStrategy implements ErrorHandleStrategy {
    private static final String MESSAGE_EXTRANEOUS_INPUT_KEYWORD = "extraneous input";
    @Override
    public boolean canHandleError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                                  String msg, RecognitionException e) {
        return msg.contains(MESSAGE_EXTRANEOUS_INPUT_KEYWORD);
    }

    @Override
    public void handleError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        Token offendingToken = (Token) offendingSymbol;
        String offendingText = offendingToken.getText();

        System.err.println("Unexpected token \""
                + offendingText
                + "\" on line: "
                + line + ":"
                + charPositionInLine);
    }
}
