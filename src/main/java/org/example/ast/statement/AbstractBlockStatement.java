package org.example.ast.statement;

import org.example.semantic.ISemanticallyAnalyzable;

public abstract class AbstractBlockStatement implements ISemanticallyAnalyzable {

    @Override
    public abstract String toString();
}
