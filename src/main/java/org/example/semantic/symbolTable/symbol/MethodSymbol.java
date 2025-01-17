package org.example.semantic.symbolTable.symbol;

import org.example.semantic.type.AbstractType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MethodSymbol extends AbstractSymbol {
    public final List<AbstractType> mParameterTypes = new ArrayList<>();

    public MethodSymbol(String name, List<AbstractType> parameterTypes) {
        super(name);
        mParameterTypes.addAll(parameterTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), mName, mParameterTypes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodSymbol other) {
            return mName.equals(other.mName) && mParameterTypes.equals(other.mParameterTypes);
        }

        return false;
    }
}
