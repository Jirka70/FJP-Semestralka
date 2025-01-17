package org.example.semantic;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.symbolTable.scope.ClassScope;

public class SemanticAnalyzer {
    public final ISemanticallyAnalyzable mAnalyzableNode;
    public final AbstractScope mSymbolTable = new ClassScope(null, null);

    public SemanticAnalyzer(ISemanticallyAnalyzable analyzableNode) {
        mAnalyzableNode = analyzableNode;
    }

    public void analyse() throws SemanticException {

        mAnalyzableNode.collectData(mSymbolTable);
        mAnalyzableNode.analyze(mSymbolTable);
    }
}
