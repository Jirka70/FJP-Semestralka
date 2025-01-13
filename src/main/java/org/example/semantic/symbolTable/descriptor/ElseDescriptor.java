package org.example.semantic.symbolTable.descriptor;

public class ElseDescriptor extends AbstractDescriptor {

    public ElseDescriptor() {
        super(DescriptorType.ELSE);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
