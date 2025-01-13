package org.example.semantic.symbolTable.descriptor;

public class IfDescriptor extends AbstractDescriptor {

    public IfDescriptor() {
        super(DescriptorType.IF);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
