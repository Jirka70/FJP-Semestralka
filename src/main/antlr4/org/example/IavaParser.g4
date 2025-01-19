parser grammar IavaParser;

options {
    tokenVocab = IavaLexer;
}

compilationUnit
    : (typeDeclaration | ';')* EOF
    ;

typeDeclaration
    : (
        classDeclaration
    )
    ;

modifier
    : classOrInterfaceModifier
    ;

classOrInterfaceModifier
    : FINAL
    ;

variableModifier
    : FINAL
    ;

classDeclaration
    : CLASS identifier classBody
    ;

classBody
    : '{' classBodyDeclaration* '}'
    ;

classBodyDeclaration
    : ';'
    | block
    | modifier* memberDeclaration
    ;

memberDeclaration
    : methodDeclaration
    | fieldDeclaration
    | classDeclaration
    ;

/* We use rule this even for void methods which cannot have [] after parameters.
   This simplifies grammar and we can consider void to be a type, which
   renders the [] matching as a context-sensitive issue or a semantic check
   for invalid return type after parsing.
 */
methodDeclaration
    : typeTypeOrVoid identifier formalParameters ('[' ']')* methodBody
    ;

methodBody
    : block
    | ';'
    ;

typeTypeOrVoid
    : typeType
    | VOID
    ;

fieldDeclaration
    : typeType variableDeclarators ';'
    ;

variableDeclarators
    : variableDeclarator (',' variableDeclarator)*
    ;

variableDeclarator
    : variableDeclaratorId ('=' variableInitializer)?
    ;

variableDeclaratorId
    : identifier ('[' ']')*
    ;

variableInitializer
    : arrayInitializer
    | expression
    ;

arrayInitializer
    : '{' (variableInitializer (',' variableInitializer)* ','?)? '}'
    ;

classOrInterfaceType
    : (identifier '.')* identifier
    ;

formalParameters
    : '(' (
        formalParameterList?
    ) ')'
    ;

formalParameterList
    : formalParameter (',' formalParameter)*
    ;

formalParameter
    : variableModifier* typeType variableDeclaratorId
    ;

literal
    : integerLiteral #IntegerLitNonTerminal
    | floatLiteral #FloatLitNonTerminal
    | CHAR_LITERAL #CharLit
    | STRING_LITERAL #StringLit
    | BOOL_LITERAL #BoolLit
    | NULL_LITERAL #NullLit
    ;

integerLiteral
    : DECIMAL_LITERAL #DecimalLit
    | HEX_LITERAL #HexLit
    | OCT_LITERAL #OctLit
    | BINARY_LITERAL #BinaryLit
    ;

floatLiteral
    : FLOAT_LITERAL #FloatLit
    | HEX_FLOAT_LITERAL #HexFloatLit
    ;

// STATEMENTS / BLOCKS

block
    : '{' blockStatement* '}'
    ;

blockStatement
    : localVariableDeclaration ';'
    | statement
    ;

localVariableDeclaration
    : variableModifier* typeType variableDeclarators
    ;

identifier
    : IDENTIFIER
    ;

statement
    : blockLabel = block
    | IF parExpression '{' statement '}' (ELSE statement)?
    | FOR '(' forControl ')' statement
    | WHILE parExpression statement
    | DO statement WHILE parExpression ';'
    | SWITCH parExpression '{' switchBlockStatementGroup* switchLabel* '}'
    | RETURN expression? ';'
    | BREAK ';'
    | CONTINUE ';'
    | SEMI
    | statementExpression = expression ';'
    ;

/** Matches cases then statements, both of which are mandatory.
 *  To handle empty cases at the end, we add switchLabel* to statement.
 */
switchBlockStatementGroup
    : switchLabel+ blockStatement+
    ;

switchLabel
    : CASE (
        constantExpression = expression
    ) ':'
    | DEFAULT ':'
    ;

forControl
    : forInit? ';' expression? ';' forUpdate = expressionList?
    ;

forInit
    : localVariableDeclaration
    | expressionList
    ;

// EXPRESSIONS

parExpression
    : '(' expression ')'
    ;

expressionList
    : expression (',' expression)*
    ;

methodCall
    : (identifier) arguments
    ;

expression
    // Expression order in accordance with https://introcs.cs.princeton.edu/java/11precedence/
    // Level 16, Primary, array and member access
    : primary
    | expression '[' expression ']'
    | expression bop = '.' (
        identifier
        | methodCall
    )
    // Method calls and method references are part of primary, and hence level 16 precedence
    | methodCall

    // Level 15 Post-increment/decrement operators
    | expression postfix = ('++' | '--')

    // Level 14, Unary operators
    | prefix = ('+' | '-' | '++' | '--' | '~' | '!') expression

    // Level 13 Cast and object creation
    | '(' typeType ')' expression
    | NEW creator

    // Level 12 to 1, Remaining operators
    | expression bop = ('*' | '/' | '%') expression           // Level 12, Multiplicative operators
    | expression bop = ('+' | '-') expression                 // Level 11, Additive operators
    | expression ('<' '<' | '>' '>' '>' | '>' '>') expression // Level 10, Shift operators
    | expression bop = ('<=' | '>=' | '>' | '<') expression   // Level 9, Relational operators
    | expression bop = ('==' | '!=') expression                      // Level 8, Equality Operators
    | expression bop = '&' expression                                // Level 7, Bitwise AND
    | expression bop = '^' expression                                // Level 6, Bitwise XOR
    | expression bop = '|' expression                                // Level 5, Bitwise OR
    | expression bop = '&&' expression                               // Level 4, Logic AND
    | expression bop = '||' expression                               // Level 3, Logic OR
    | <assoc = right> expression bop = '?' expression ':' expression // Level 2, Ternary
    // Level 1, Assignment
    | <assoc = right> expression bop = (
        '='
        | '+='
        | '-='
        | '*='
        | '/='
        | '&='
        | '|='
        | '^='
        | '>>='
        | '>>>='
        | '<<='
        | '%='
    ) expression
    ;

primary
    : '(' expression ')'
    | literal
    | identifier
    ;

creator
    : createdName classCreatorRest
    | createdName arrayCreatorRest
    ;

createdName
    : identifier ('.' identifier)*
    | primitiveType
    ;

arrayCreatorRest
    : ('[' ']')+ arrayInitializer
    | ('[' expression ']')+ ('[' ']')*
    ;

classCreatorRest
    : arguments classBody?
    ;

typeType
    : (classOrInterfaceType | primitiveType) ('[' ']')*
    ;

primitiveType
    : BOOLEAN
    | CHAR
    | INT
    | FLOAT
    ;

arguments
    : '(' expressionList? ')'
    ;
