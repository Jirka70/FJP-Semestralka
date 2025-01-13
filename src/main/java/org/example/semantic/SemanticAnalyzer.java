package org.example.semantic;

import org.example.semantic.exception.SemanticException;
import org.example.semantic.symbolTable.SymbolTable;

public class SemanticAnalyzer {
    public final ISemanticallyAnalyzable mAnalyzableNode;
    public final SymbolTable mSymbolTable = new SymbolTable();

    public SemanticAnalyzer(ISemanticallyAnalyzable analyzableNode) {
        mAnalyzableNode = analyzableNode;
    }

    public void analyse() throws SemanticException {
        mAnalyzableNode.collectData(null);
        mAnalyzableNode.analyze(mSymbolTable);
    }
}
