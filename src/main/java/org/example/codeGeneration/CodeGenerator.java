package org.example.codeGeneration;

import org.example.semantic.symbolTable.descriptor.AbstractDescriptor;
import org.example.semantic.symbolTable.descriptor.DescriptorType;
import org.example.semantic.symbolTable.descriptor.VariableDescriptor;
import org.example.semantic.symbolTable.scope.AbstractScope;
import org.example.semantic.type.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {
    public final IGeneratable mGeneratableNode;
    private long mInstructionCounter;
    private final Map<String, Long> mCodeLabels = new HashMap<>();
    public final Map<String, Integer> mCurrentStackFrameMappings = new HashMap<>(); // TODO: public only for debug
    private int mCurrentStackFrameSize;

    private final List<String> instructions = new ArrayList<>();

    public CodeGenerator(IGeneratable generatableNode) {
        mGeneratableNode = generatableNode;
        mInstructionCounter = 0;
    }

    public void generate(AbstractScope rootScope) {
        System.out.println("-------------------------------");
        System.out.println("Code generation started");
        mGeneratableNode.generate(rootScope, this);
        System.out.println(mCodeLabels);

        // TODO - zatím provizorně
        System.out.println();
        instructions.stream().forEach(s -> {
            for (Map.Entry<String, Long> entry : mCodeLabels.entrySet()) {
                if (s.contains(entry.getKey())) s = s.replace(entry.getKey(), entry.getValue().toString());
            }
            System.out.println(s);
        } );
//        instructions.forEach(System.out::println);

    }

    public void addInstruction(String instruction) {
        System.out.println(instruction);
        instructions.add(instruction);
        mInstructionCounter++;
    }

    public void addCodeLabel(String label) {
        if (mCodeLabels.containsKey(label))
            throw new RuntimeException("Multiple definitions of code label " + label);
        mCodeLabels.put(label, mInstructionCounter);
    }

    public void newStackFrame() {
        final int ACTIVATION_RECORD_SIZE = 3;

        mCurrentStackFrameMappings.clear();
        mCurrentStackFrameSize = ACTIVATION_RECORD_SIZE;
    }

    public void mapLocalVariables(AbstractScope scope) {
        for (AbstractScope childScope : scope.mChildrenScopes.values()) {
            mapLocalVariables(childScope);
        }
        for (AbstractDescriptor descriptor : scope.getAllDescriptors()) {
            if (descriptor.mDescriptorType.equals(DescriptorType.VARIABLE_DESCRIPTOR)) {
                VariableDescriptor varDescriptor = (VariableDescriptor) descriptor;
                int varSize = typeSize(varDescriptor.mType);
                addStackFrameMapping(varDescriptor.mName, varSize);
            }
        }
    }

    public int typeSize(String typeStr) {
        if ("vacuum".equals(typeStr))
            return 0;

        AbstractType type = TypeFactory.fromString(typeStr);
        if (type instanceof FloatType)
            return 2;
        else if (type instanceof PrimitiveType || type instanceof ObjectType)
            return 1;

        throw new RuntimeException("Size of type " + type + " is unknown");
    }

    private void addStackFrameMapping(String name, int size) {
        if (mCurrentStackFrameMappings.containsKey(name))
            throw new RuntimeException("Multiple definitions of variable " + name + " in the same stack frame");
        mCurrentStackFrameMappings.put(name, mCurrentStackFrameSize);
        mCurrentStackFrameSize += size;
    }

    public int getStackFrameSize() {
        return mCurrentStackFrameSize;
    }

    public int getStackFrameAddress(String name) {
        return mCurrentStackFrameMappings.get(name);
    }
}
