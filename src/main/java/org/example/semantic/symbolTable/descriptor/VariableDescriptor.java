package org.example.semantic.symbolTable.descriptor;

import java.util.Objects;

public class VariableDescriptor extends AbstractDescriptor {
    private static final String VOID_TYPE = "vacuum";
    public final String mName;
    public final String mType;
    public final boolean mIsAssigned;
    public final boolean mIsFinal;

    public VariableDescriptor(String name, String type, boolean isAssigned, boolean isFinal) {
        super(DescriptorType.VARIABLE_DESCRIPTOR);

        if (type.equals(VOID_TYPE)) {
            throw new IllegalArgumentException("Variable cannot have " + VOID_TYPE + " type");
        }

        mName = name;
        mType = type;
        mIsAssigned = isAssigned;
        mIsFinal = isFinal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mType, mIsAssigned, mIsFinal);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VariableDescriptor other) {
            return mName.equals(other.mName)
                    && mType.equals(other.mType)
                    && mIsAssigned == other.mIsAssigned
                    && mIsFinal == other.mIsFinal;
        }

        return false;
    }

    @Override
    public String toString() {
        return "VariableDescriptor: "
                + "name="
                + mName
                + ", type="
                + mType
                + ", isAssigned="
                + mIsAssigned
                + ", isFinal="
                + mIsFinal;
    }
}
