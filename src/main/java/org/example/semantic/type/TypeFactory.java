package org.example.semantic.type;

import java.util.HashMap;
import java.util.Map;

public class TypeFactory {
    private static final Map<String, AbstractType> typeMap = new HashMap<>();

    static {
        typeMap.put("int", new IntType());
        typeMap.put("float", new FloatType());
        typeMap.put("char", new CharType());
        typeMap.put("boolean", new BooleanType());
        typeMap.put("vacuum", new VoidType());
    }

    public static AbstractType fromString(String type) {
        if (typeMap.containsKey(type)) {
            return typeMap.get(type);
        }

        return new ObjectType(type);
    }
}
