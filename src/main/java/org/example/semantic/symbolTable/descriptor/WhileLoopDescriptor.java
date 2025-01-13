package org.example.semantic.symbolTable.descriptor;

public class WhileLoopDescriptor extends AbstractDescriptor {

    public WhileLoopDescriptor() {
        super(DescriptorType.WHILE_LOOP);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
