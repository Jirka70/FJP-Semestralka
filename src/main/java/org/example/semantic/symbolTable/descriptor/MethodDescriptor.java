package org.example.semantic.symbolTable.descriptor;

public class MethodDescriptor extends AbstractDescriptor {
    public final String mMethodGame;
    public final String mReturnType;

    public MethodDescriptor(String methodName, String returnType) {
        super(DescriptorType.METHOD_DESCRIPTOR);
        mMethodGame = methodName;
        mReturnType = returnType;
    }

    @Override
    public String toString() {
        return "MethodDescriptor: methodName="
                + mMethodGame
                + ", returnType="
                + mReturnType;
    }
}