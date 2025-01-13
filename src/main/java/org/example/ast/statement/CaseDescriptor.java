package org.example.ast.statement;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.DescriptorType;

public class CaseDescriptor extends AbstractDescriptor {

    public CaseDescriptor() {
        super(DescriptorType.CASE);
    }
}
