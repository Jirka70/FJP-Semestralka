package org.example.primitive.clazz.method;

import org.example.primitive.block.Block;

public class MethodBody {
    public final Block mBlock;

    public MethodBody(Block block) {
        mBlock = block;
    }

    @Override
    public String toString() {
        return "MethodBody: " + mBlock;
    }
}