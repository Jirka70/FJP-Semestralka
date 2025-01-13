package org.example.semantic.symbolTable.descriptor;

public class AppDescriptor extends AbstractDescriptor {
    public AppDescriptor() {
        super(DescriptorType.APP_DESCRIPTOR);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
