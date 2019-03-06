package diagram;

import com.sun.istack.internal.NotNull;
import object.TypeData;
import parser.Scanner;
import parser.Token;
import parser.TokenType;

public class MyDiagram {
    private Scanner scanner;

    // todo oooooooo
    public void program () {
        nextToken(TokenType.CLASS, "Ожидался класс");
        nextToken(TokenType.MAIN, "Ожидался Main");
        Token token = nextTokenRead();
        while (token.getType() != TokenType.Close_Braces && token.getType() != TokenType.EOF) {

            token = nextTokenRead();
        }
    }

    private boolean isOperatorsAndData (Token token) {
        return token.getType() == TokenType.Open_Braces;
    }

    private void operatorsAndData () {
        nextToken(TokenType.Open_Braces, "Ожидался символ {");
        Token token = nextTokenRead();
        // тут была открывающаяся скобка
        while (token.getType() != TokenType.Close_Braces && token.getType() != TokenType.EOF) {
            if (isOperator(token))
                operator();
            else if (isData(token))
                data();
            else
                printError("Неизвестный символ");
            token = nextTokenRead();
        }
        nextToken(TokenType.Close_Braces, "Ожидался символ }");
    }


    private boolean isData (Token token) {
        return token.getType() == TokenType.DOUBLE ||
                token.getType() == TokenType.INT;
    }

    private void data () {
        Token token = scanner.nextScanner();
        TypeData typeData;

        if (token.getType() == TokenType.INT) {
            typeData = TypeData.INTEGER;
        }
        else {
            typeData = TypeData.DOUBLE;
        }

        token = nextTokenRead();
        if (isVariable(token)) {
            variable(typeData);
        }
        else {
            printError("Ожидался идентификатор");
        }

        token = nextTokenRead();
        while (token.getType() == TokenType.Comma) {
            scanner.nextScanner();
            token = nextTokenRead();
            if (isVariable(token)) {
                variable(typeData);
            }
            else {
                printError("Ожидался идентификатор");
            }
            token = nextTokenRead();
        }
        nextToken(TokenType.Semicolon, "Ожидался символ ;");
    }

    private boolean isVariable (Token token) {
        return token.getType() == TokenType.Id;
    }

    private void variable (TypeData typeData) {
        Token varName = nextToken(TokenType.Id, "Ожидался ID");


    }



    private boolean isExpression(Token token) {
        return isExpression2(token);
    }

    private boolean isExpression2 (Token token) {
        return isExpression3(token);
    }

    // выражение 3
    private boolean isExpression3 (Token token) {
        return isExpression4(token);
    }

    // выражение 4
    private boolean isExpression4 (Token token) {
        return isExpression5(token);
    }

    // выражение 5
    private boolean isExpression5 (Token token) {
        return isExpression6(token) ||
                token.getType() == TokenType.Plus ||
                token.getType() == TokenType.Minus;
    }

    // выражение 6
    private boolean isExpression6 (Token token) {
        return token.getType() == TokenType.Id ||
                token.getType() == TokenType.Open_Breaket ||
                token.getType() == TokenType.Type_Dec_Int ||
                token.getType() == TokenType.Type_Hex_Int ||
                token.getType() == TokenType.Type_Oct_Int ||
                token.getType() == TokenType.Const_Char;
    }


    private Token nextToken (TokenType type, String text) {
        Token token = scanner.nextScanner();
        if (token.getType() != type) {
            printError(text);
        }
        return token;
    }

    private Token nextTokenRead() {
        scanner.save();
        Token token = scanner.nextScanner();
        scanner.ret();
        return token;
    }

    public void printError(String text) {
        System.out.println(text + " строка " + scanner.getNumberRow() + ", столбец " + scanner.getNumberCol());
        System.exit(1);
    }

    private void printSemError(String text) {
        System.out.println(text + " строка " + scanner.getNumberRow() + ", столбец " + scanner.getNumberCol());
    }
}
