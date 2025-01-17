package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class VariableNotAssignedException extends SemanticException {

    public VariableNotAssignedException(String message) {
        super(message);
    }
}
