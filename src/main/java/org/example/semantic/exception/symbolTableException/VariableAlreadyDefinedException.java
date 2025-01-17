package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class VariableAlreadyDefinedException extends SemanticException {

    public VariableAlreadyDefinedException(String message) {
        super(message);
    }
}
