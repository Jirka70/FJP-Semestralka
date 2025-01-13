package org.example.ast.statement;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.DescriptorType;

public class DoWhileDescriptor extends AbstractDescriptor {

    protected DoWhileDescriptor() {
        super(DescriptorType.DO_WHILE);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
