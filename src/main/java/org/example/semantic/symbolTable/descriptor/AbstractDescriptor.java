package org.example.semantic.symbolTable.descriptor;

public abstract class AbstractDescriptor {
    public final DescriptorType mDescriptorType;

    protected AbstractDescriptor(DescriptorType type) {
        mDescriptorType = type;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + mDescriptorType;
    };
}
