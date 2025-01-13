package org.example.ast.statement;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.DescriptorType;

public class SwitchDescriptor extends AbstractDescriptor {

    protected SwitchDescriptor() {
        super(DescriptorType.SWITCH);
    }
}
