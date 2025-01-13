package org.example.semantic;

import org.example.semantic.symbolTable.SymbolTable;
import org.example.semantic.symbolTable.scope.Scope;

public interface ISemanticallyAnalyzable {
    void analyze(SymbolTable symbolTable);
    void collectData(Scope currentScope);
}
