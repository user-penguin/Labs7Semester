package parser;

public enum TokenType {
    Type_Dec_Int,
    Type_Oct_Int,
    Type_Hex_Int,
    Const_Char,
    DOUBLE,
    INT,
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


    MAIN,
    CLASS,
    NEW,
    FOR,

    ERROR,
    EOF
}
