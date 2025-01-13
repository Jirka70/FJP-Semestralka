package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class UndefinedMethodException extends SemanticException {

    public UndefinedMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
