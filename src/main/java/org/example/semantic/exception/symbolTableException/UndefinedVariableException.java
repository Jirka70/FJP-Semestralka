package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class UndefinedVariableException extends SemanticException {

    public UndefinedVariableException(String message, Throwable cause) {
        super(message, cause);
    }
}
