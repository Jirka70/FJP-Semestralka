package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class UndefinedIdentifierException extends SemanticException {

    public UndefinedIdentifierException(String message) {
        super(message);
    }
}
