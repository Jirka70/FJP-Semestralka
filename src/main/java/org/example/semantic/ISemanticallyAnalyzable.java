package org.example.semantic;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;

public interface ISemanticallyAnalyzable {
    void analyze(AbstractScope abstractScope) throws SemanticException;
    void collectData(AbstractScope currentAbstractScope) throws SemanticException;
}
