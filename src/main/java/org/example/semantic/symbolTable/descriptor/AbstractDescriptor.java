package org.example.semantic.symbolTable.descriptor;

public abstract class AbstractDescriptor {
    public final DescriptorType mDescriptorType;

    protected AbstractDescriptor(DescriptorType type) {
        mDescriptorType = type;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return getClass().equals(obj.getClass());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + mDescriptorType;
    }
}
