package org.example.semantic.exception.symbolTableException;

import org.example.semantic.exception.SemanticException;

public class FinalVariableOverwrittenException extends SemanticException {

    public FinalVariableOverwrittenException(String message) {
        super(message);
    }
}
