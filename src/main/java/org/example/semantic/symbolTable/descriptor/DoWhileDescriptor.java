package org.example.semantic.symbolTable.descriptor;

public class DoWhileDescriptor extends AbstractDescriptor {

    public DoWhileDescriptor() {
        super(DescriptorType.DO_WHILE);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
