package org.example.errorHandle.errorListener;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.example.errorHandle.ErrorHandleStrategy;
import org.example.errorHandle.impl.ExpressionErrorStrategy;
import org.example.errorHandle.impl.ExtraneousInputErrorStrategy;
import org.example.errorHandle.impl.NoViableAlternativeErrorStrategy;
import org.example.errorHandle.impl.VariableInitializerErrorStrategy;

import java.util.ArrayList;
import java.util.List;

public class IavaSyntaxErrorListener extends BaseErrorListener {
    private static final int EXIT_STATUS = 1;
    public final List<ErrorHandleStrategy> mErrorHandleStrategies = new ArrayList<>();

    public IavaSyntaxErrorListener() {
        mErrorHandleStrategies.add(new VariableInitializerErrorStrategy());
        mErrorHandleStrategies.add(new ExpressionErrorStrategy());
        mErrorHandleStrategies.add(new ExtraneousInputErrorStrategy());
        mErrorHandleStrategies.add(new NoViableAlternativeErrorStrategy());
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        for (ErrorHandleStrategy errorHandleStrategy : mErrorHandleStrategies) {
            if (errorHandleStrategy.canHandleError(recognizer, offendingSymbol, line, charPositionInLine, msg, e)) {
                errorHandleStrategy.handleError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
                System.exit(EXIT_STATUS);
            }
        }

        System.err.println("Unexpected syntax error: " + msg + " on line: " + line + ":" + charPositionInLine);
        System.exit(EXIT_STATUS);
    }
}
