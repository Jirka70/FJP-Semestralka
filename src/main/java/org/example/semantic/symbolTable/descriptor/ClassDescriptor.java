package org.example.semantic.symbolTable.descriptor;

public class ClassDescriptor extends AbstractDescriptor {
    public final String mClassName;

    public ClassDescriptor(String className) {
        super(DescriptorType.CLASS_DESCRIPTOR);
        mClassName = className;
    }

    @Override
    public String toString() {
        return "";
    }
}
