package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class UndefinedTypeException extends SemanticException {

    public UndefinedTypeException(String message) {
        super(message);
    }
}
