package parser;

public enum TokenType {
    INT,
    INT8,
    INT16,
    Const_Char,
    DOUBLE,
    Id,

    Plus,
    Minus,
    Slash,
    Star,
    Percent,

    Comma,
    Open_Square,
    Close_Square,

    Less,
    Great,
    Less_Eq,
    Great_Eq,
    Eq,
    Not_Eq,

    Semicolon,
    Open_Breaket,
    Close_Breaket,
    Open_Braces,
    Close_Braces,
    Assign,


    MAIN_CLASS,
    MAIN_FUNC,
    CLASS,
    NEW,
    FOR,

    ERROR,
    EOF,

    VOID,
    STATIC,
    PUBLIC
}
