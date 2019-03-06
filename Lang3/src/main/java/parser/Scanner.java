package parser;

public class Scanner {
    private String text;
    private int numberRow;
    private int numberCol;
    private int numberSymbol;

    private int oldNumberRow;
    private int oldNumberCol;
    private int oldNumberSymbol;

    private String tokenMathSymbols = "+-/*%;(){},[]";
    private TokenType[] tokenMathMass = {
            TokenType.Plus,
            TokenType.Minus,
            TokenType.Slash,
            TokenType.Star,
            TokenType.Percent,
            TokenType.Semicolon,
            TokenType.Open_Breaket,
            TokenType.Close_Breaket,
            TokenType.Open_Braces,
            TokenType.Close_Braces,
            TokenType.Comma,
            TokenType.Open_Square,
            TokenType.Close_Square
    };
    private String tokenCompareSymbols = "<>=";
    private TokenType[] tokenCompareMass = {TokenType.Less, TokenType.Great, TokenType.Assign};
    private TokenType[] tokenCompareEquallyMass = {TokenType.Less_Eq, TokenType.Great_Eq, TokenType.Eq};

    public Scanner(String text) {
        this.text = text;
        numberRow = 1;
        numberCol = 1;
    }

    public Token  nextScanner() {
        ignoringSymbols();

        if (isIdSymbol(text.charAt(numberSymbol)))
            return scannerID();
        if (isCompare(text.charAt(numberSymbol)))
            return scannerCompare();
        if (text.charAt(numberSymbol) == '!')
            return scannerNotEqually();
        if (text.charAt(numberSymbol) == '\'')
            return scannerChar();
        if (isDigit(text.charAt(numberSymbol)))
            return scannerNumber();
        if (isHexDigit(text.charAt(numberSymbol)))
            return scannerNumber();
        if (isOctDigit(text.charAt(numberSymbol)))
            return scannerNumber();
        if (isMathOrSpec(text.charAt(numberSymbol)))
            return scannerMathOrSpec();
        if (text.charAt(numberSymbol) == '\0')
            return scannerEof();
        else {
            char ch = text.charAt(numberSymbol);
            addNumberSymbol();
            return scannerError("Неизвестный символ \'" + ch + "\'");
        }
    }

    private void ignoringSymbols() {
        boolean canScanner = false;
        while (!canScanner) {
            canScanner = true;
            while (text.charAt(numberSymbol) == ' ' || text.charAt(numberSymbol) == '\t' || text.charAt(numberSymbol) == '\n' || text.charAt(numberSymbol) == '\r') {
                addNumberSymbol();
                canScanner = false;
            }
            while (text.charAt(numberSymbol) == '#') {
                while (text.charAt(numberSymbol) != '\n')
                    addNumberSymbol();
                addNumberSymbol();
                canScanner = false;
            }
        }
    }

    private Token scannerID() {
        String str = "" + text.charAt(numberSymbol);
        addNumberSymbol();
        while (isIdSymbol(text.charAt(numberSymbol)) || isDigit(text.charAt(numberSymbol))) {
            str += text.charAt(numberSymbol);
            addNumberSymbol();
        }

        if (str.equals("double"))
            return new Token(TokenType.DOUBLE);
        if (str.equals("int"))
            return new Token(TokenType.INT);
        if (str.equals("main"))
            return new Token(str, TokenType.MAIN);
        if (str.equals("class"))
            return new Token(str, TokenType.CLASS);
        if (str.equals("new"))
            return new Token(str, TokenType.NEW);
        if (str.equals("for"))
            return new Token(str, TokenType.FOR);

        return  new Token(str, TokenType.Id);
    }

    private Token scannerCompare() {
        int index = tokenCompareSymbols.indexOf(text.charAt(numberSymbol));
        addNumberSymbol();
        if (text.charAt(numberSymbol) == '=') {
            addNumberSymbol();
            return new Token(tokenCompareEquallyMass[index]);
        } else
            return  new Token(tokenCompareMass[index]);
    }

    private Token scannerNotEqually() {
        addNumberSymbol();
        if (text.charAt(numberSymbol) == '=') {
            addNumberSymbol();
            return new Token(TokenType.Not_Eq);
        }
        else
            return scannerError("Ошибка считывания Not_Eq");
    }

    private Token scannerChar() {
        addNumberSymbol();
        if(text.charAt(numberSymbol + 1) == '\'') {
            numberSymbol += 2;
            return new Token(text.charAt(numberSymbol - 2) + "", TokenType.Const_Char);
        } else
            return scannerError("Ошибка считывания Const_Char");
    }

    private Token scannerNumber() {
        String str = "" + text.charAt(numberSymbol);
        if (text.charAt(numberSymbol) != '0') {
            addNumberSymbol();
            while (isDigit(text.charAt(numberSymbol))) {
                str += text.charAt(numberSymbol);
                addNumberSymbol();
            }
            try {
                Integer.parseInt(str);
                return new Token(str, TokenType.Type_Dec_Int);
            } catch (NumberFormatException e) {
                return scannerError("Ошибка считывания Type_Dec_Int слишком длинный");
            }
        }
        else {
            addNumberSymbol();
            if (isOctDigit(text.charAt(numberSymbol)) && text.charAt(numberSymbol) != '0') {
                str += text.charAt(numberSymbol);
                addNumberSymbol();
                while (isOctDigit(text.charAt(numberSymbol))) {
                    str += text.charAt(numberSymbol);
                    addNumberSymbol();
                }
                try {
                    Integer.parseInt(str);
                    return new Token(str, TokenType.Type_Oct_Int);
                } catch (NumberFormatException e) {
                    return scannerError("Ошибка считывания Type_Oct_Int слишком длинный");
                }
            }
            else
                if (text.charAt(numberSymbol) == 'x' || text.charAt(numberSymbol) == 'X') {
                    str += text.charAt(numberSymbol);
                    addNumberSymbol();
                    if (isHexDigit(text.charAt(numberSymbol)) && text.charAt(numberSymbol) != 0) {
                        addNumberSymbol();
                        while (isHexDigit(text.charAt(numberSymbol))) {
                            str += text.charAt(numberSymbol);
                            addNumberSymbol();
                        }
                        try {
                            Integer.decode(str);
                            return new Token(str, TokenType.Type_Hex_Int);
                        } catch (NumberFormatException e) {
                            return scannerError("Ошибка считывания Type_Hex_Int слишком длинный");
                        }
                    }
                }
        }
        return scannerError("Ошибка при инициализации Int");
    }

    private Token scannerMathOrSpec() {
        int index = tokenMathSymbols.indexOf(text.charAt(numberSymbol));
        addNumberSymbol();
        return new Token(tokenMathMass[index]);
    }

    private Token scannerEof() {
        return new Token(TokenType.EOF);
    }

    private Token scannerError(String text) {
        return new Token(text + " row " + numberRow + " col " + numberCol + " ", TokenType.ERROR);
    }

    private boolean isIdSymbol(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||  c == '_';
    }

    // десятичная цифра
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    // шестнадцатиричная цифрра
    private boolean isHexDigit(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
    }

    // восьмеичная цифра
    private boolean isOctDigit(char c) {
        return c >= '0' && c <= '8';
    }

    private boolean isCompare(char c) {
        return tokenCompareSymbols.indexOf(text.charAt(numberSymbol)) != -1;
    }

    private boolean isMathOrSpec(char c) {
        return tokenMathSymbols.indexOf(text.charAt(numberSymbol)) != -1;
    }

    private void addNumberSymbol() {
        numberCol++;
        if(text.charAt(numberSymbol) == '\n') {
            numberRow++;
            numberCol = 1;
        }
        numberSymbol++;
    }

    public int getNumberRow() {
        return numberRow;
    }

    public int getNumberCol() {
        return numberCol;
    }

    public void save() {
        oldNumberCol = numberCol;
        oldNumberRow = numberRow;
        oldNumberSymbol = numberSymbol;
    }

    public void ret() {
        numberCol = oldNumberCol;
        numberRow = oldNumberRow;
        numberSymbol = oldNumberSymbol;
    }
}
