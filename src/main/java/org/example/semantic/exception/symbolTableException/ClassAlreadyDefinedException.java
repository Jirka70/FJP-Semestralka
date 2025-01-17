package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class ClassAlreadyDefinedException extends SemanticException {

    public ClassAlreadyDefinedException(String message) {
        super(message);
    }
}
