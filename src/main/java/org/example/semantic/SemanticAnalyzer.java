package org.example.semantic;

public class SemanticAnalyzer {
    public final ISemanticallyAnalyzable mAnalyzableNode;
    public final SymbolTable mSymbolTable = new SymbolTable();

    public SemanticAnalyzer(ISemanticallyAnalyzable analyzableNode) {
        mAnalyzableNode = analyzableNode;
    }

    public void analyse() {
        mAnalyzableNode.analyze(mSymbolTable);
    }
}
