package org.example.semantic.symbolTable.descriptor;

public class ForLoopDescriptor extends AbstractDescriptor {

    public ForLoopDescriptor() {
        super(DescriptorType.FOR_LOOP);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
