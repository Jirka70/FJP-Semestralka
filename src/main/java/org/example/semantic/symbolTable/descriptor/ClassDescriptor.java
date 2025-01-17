package org.example.semantic.symbolTable.descriptor;

public class ClassDescriptor extends AbstractDescriptor {
    public final String mClassName;

    public ClassDescriptor(String className) {
        super(DescriptorType.CLASS_DESCRIPTOR);
        mClassName = className;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassDescriptor other) {
            return mClassName.equals(other.mClassName);
        }

        return false;
    }
}
