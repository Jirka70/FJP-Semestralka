package org.example.statement;

import java.util.ArrayList;
import java.util.List;

public class LocalVariableDeclaration extends AbstractStatement {
    public final List<String> mModifiers = new ArrayList<>();
    public final String mType;
    public final List<LocalVariable> mLocalVariables = new ArrayList<>();

    public LocalVariableDeclaration(List<String> modifiers, String type, List<LocalVariable> localVariables) {
        super(StatementType.LOCAL_VARIABLE_DECLARATION);
        mModifiers.addAll(modifiers);
        mType = type;
        mLocalVariables.addAll(localVariables);
    }

    @Override
    public String toString() {
        return "LocalVariableDeclaration: "
                + "Modifiers: "
                + mModifiers
                + ", Type: "
                + mType
                + ", Local variables: "
                + mLocalVariables;
    }
}
