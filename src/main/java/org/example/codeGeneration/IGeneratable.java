package org.example.codeGeneration;

import org.example.semantic.symbolTable.scope.AbstractScope;

public interface IGeneratable {
    void generate(AbstractScope currentAbstractScope, CodeGenerator generator);
}
