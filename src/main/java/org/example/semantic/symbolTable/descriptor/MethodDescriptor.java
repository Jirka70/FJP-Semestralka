package org.example.semantic.symbolTable.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MethodDescriptor extends AbstractDescriptor {
    public final String mMethodName;
    public final String mFullMethodName;
    public final String mReturnType;
    public final List<VariableDescriptor> mFormalVariableDescriptors = new ArrayList<>();

    public MethodDescriptor(String methodName, String returnType,
                            List<VariableDescriptor> formalParametersDescriptors, String fullMethodName) {
        super(DescriptorType.METHOD_DESCRIPTOR);
        mMethodName = methodName;
        mReturnType = returnType;
        mFormalVariableDescriptors.addAll(formalParametersDescriptors);
        mFullMethodName = fullMethodName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mFullMethodName, mReturnType, mFormalVariableDescriptors);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodDescriptor other) {
            return mFullMethodName.equals(other.mFullMethodName)
                    && mReturnType.equals(other.mReturnType)
                    && areOtherFormalParametersDescriptorsSame(other.mFormalVariableDescriptors);
        }

        return false;
    }


    private boolean areOtherFormalParametersDescriptorsSame(List<VariableDescriptor> otherFormalParametersDescriptors) {
        if (otherFormalParametersDescriptors.size() != mFormalVariableDescriptors.size()) return false;

        for (int i = 0; i < otherFormalParametersDescriptors.size(); i++) {
            VariableDescriptor currentParameter = mFormalVariableDescriptors.get(i);
            VariableDescriptor otherParameter = otherFormalParametersDescriptors.get(i);

            if (!currentParameter.mType.equals(otherParameter.mType)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "MethodDescriptor: methodName="
                + mFullMethodName
                + ", returnType="
                + mReturnType;
    }
}