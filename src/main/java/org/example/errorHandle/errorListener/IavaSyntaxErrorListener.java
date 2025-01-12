package org.example.errorHandle.errorListener;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class IavaSyntaxErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        System.out.println("offendingSymbol: " + offendingSymbol);
        super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
    }
}
