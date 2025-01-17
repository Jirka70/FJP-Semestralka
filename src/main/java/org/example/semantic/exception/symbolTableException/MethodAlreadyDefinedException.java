package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class MethodAlreadyDefinedException extends SemanticException {

    public MethodAlreadyDefinedException(String message) {
        super(message);
    }
}
