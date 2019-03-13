package diagram;

import object.TypeData;
import parser.Scanner;
import parser.Token;
import parser.TokenType;

public class MyDiagram {
    private Scanner scanner;

    public MyDiagram (Scanner scanner) {
        this.scanner = scanner;
    }

    // todo oooooooo
    public void program () {
        nextToken(TokenType.CLASS, "Ожидался класс");
        nextToken(TokenType.MAIN_CLASS, "Ожидался Main");
        nextToken(TokenType.Open_Braces, "Ожидался символ {");

        Token token = nextTokenRead();
        while (token.getType() != TokenType.Close_Braces && token.getType() != TokenType.EOF) {
            if (isFunction(token))
                function();
            else if (isData(token))
                data();
            else
                printError("Неизвестный символ");
            token = nextTokenRead();
        }
        nextToken(TokenType.Close_Braces, "Ожидался символ }");
    }

    private boolean isFunction(Token token) {
        return token.getType() == TokenType.VOID || token.getType() == TokenType.PUBLIC;
    }

    private void function() {
        Token token = scanner.nextScanner();

        Token tokenFunction;
        if (token.getType() == TokenType.VOID) {
            tokenFunction = nextToken(TokenType.Id, "Ожидался идентификатор");
        } else {
            nextToken(TokenType.VOID, "Ожидался void");
            tokenFunction = nextToken(TokenType.MAIN_FUNC, "Ожидался main");
        }

        nextToken(TokenType.Open_Breaket, "Ожидался символ (");
        nextToken(TokenType.Close_Breaket, "Ожидался символ )");

        token = nextTokenRead();
        if (isOperatorsAndData(token))
            operatorsAndData();
        else
            printError("Ожидался символ {");
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

        boolean init = false;

        Token token = nextTokenRead();
        // выражение
        if (token.getType() == TokenType.Assign) {
            nextToken(TokenType.Assign, "Ожидался символ =");
            token = nextTokenRead();
            if (isExpression(token))
                expression();
            else
                printError("Ожидалось выражение");
        }
        // массив
        else if (token.getType() == TokenType.Open_Square) {
            nextToken(TokenType.Open_Square, "Ожидался символ [");
            nextToken(TokenType.Close_Square, "Ожидался символ ]");

            if (nextTokenRead().getType() == TokenType.Assign) {
                nextToken(TokenType.Assign, "Ожидался символ =");
                nextToken(TokenType.NEW, "Ожидался символ new");
                token = scanner.nextScanner();

                TypeData typeDataOfArray;
                if (token.getType() != TokenType.INT && token.getType() != TokenType.DOUBLE)
                    printError("Ожидался тип");
                if (token.getType() == TokenType.DOUBLE)
                    typeDataOfArray = TypeData.DOUBLE;
                else
                    typeDataOfArray = TypeData.INTEGER;
                // @TODO: перепилить под норм целочисленку

                nextToken(TokenType.Open_Square, "Ожидался символ [");
                Token tokenN = nextToken(TokenType.Type_Dec_Int, "Ожидалось целое");
                nextToken(TokenType.Close_Square, "Ожидался символ ]");
            }
            return;
        }
        else
            printError("Неизветный символ");
    }

    private boolean isOperator(Token token) {
        return token.getType() == TokenType.Semicolon ||
                isOperatorsAndData(token) ||
                isLoopFor(token) ||
                token.getType() == TokenType.Id;
    }

    private void operator() {
        Token token = nextTokenRead();

        if (token.getType() == TokenType.Semicolon)
            nextToken(TokenType.Semicolon, "Ожидался символ ;");
        else if (isOperatorsAndData(token)) {
            operatorsAndData();
        }
        else if (isLoopFor(token))
            loopFor();
        else if (token.getType() == TokenType.Id) {

            Token tokenName = nextToken(TokenType.Id, "Ожидался идентификатор");

            token = nextTokenRead();
            if (isAssignment(token)) {
                assignment(tokenName);
            }
            else if (token.getType() == TokenType.Open_Breaket) {
                nextToken(TokenType.Open_Breaket, "Ожидался символ (");
                nextToken(TokenType.Close_Breaket, "Ожидался символ )");
                nextToken(TokenType.Semicolon, "Ожидался символ ;");
            }
            else if (token.getType() == TokenType.Open_Square) {
                nextToken(TokenType.Open_Square, "Ожидался символ [");
                Token indexArray = nextToken(TokenType.Type_Dec_Int, "Ожидалось целое");
                nextToken(TokenType.Close_Square, "Ожидался символ ]");

                token = nextTokenRead();
                if (isAssignment(token))
                    assignment(tokenName);
                nextToken(TokenType.Semicolon, "Ожидался символ ;");

            } else {
                printError("Неизвестная команда");
            }
        } else
            printError("Неизвестный оператор");

    }



    private boolean isAssignment(Token token) {
        return token.getType() == TokenType.Assign;
    }

    private void assignment(Token tokenName) {
        nextToken(TokenType.Assign, "Ожидался символ =");
        Token token = nextTokenRead();
        if (isExpression(token)) {
            expression();
        } else  {
            nextToken(TokenType.NEW, "Ожидался new");
            token = scanner.nextScanner();
            TokenType typeMass =  token.getType();
            if (typeMass != TokenType.DOUBLE && typeMass != TokenType.INT)
                printError("Ожидался тип");
            nextToken(TokenType.Open_Square, "Ожидался символ [");
            Token tokenN = nextToken(TokenType.Type_Dec_Int, "Ожидалось целое");

            nextToken(TokenType.Close_Square, "Ожидался символ ]");
        }
    }

    private boolean isLoopFor (Token token) {
        return token.getType() == TokenType.FOR;
    }

    private void loopFor() {
        nextToken(TokenType.FOR, "Ожидался for");
        nextToken(TokenType.Open_Breaket, "Ожидался символ (");
        Token token = nextTokenRead();
        if (token.getType() == TokenType.Id) {
            nextToken(TokenType.Id, "Ожидался идентификатор");
            if (nextTokenRead().getType() == TokenType.Assign) {
                nextToken(TokenType.Assign, "Ожидалось присваивание");
                if (isExpression(nextTokenRead())) {
                    expression();
                }
            }
        }
        else {
            printError("Ожидалось выражение");
        }

        nextToken(TokenType.Semicolon, "Ожидался символ ;");
        token = nextTokenRead();
        if (isExpression(token)) {
            expression();
        }
        else {
            printError("Ожидалось выражение");
        }
        nextToken(TokenType.Semicolon, "Ожидался символ ;");
        token = nextTokenRead();
        if (token.getType() == TokenType.Id) {
            nextToken(TokenType.Id, "Ожидалось присваивание");
            token = nextTokenRead();
            if (isAssignment(token)) {
                assignment(token);
            }
            else {
                printError("Ожидалось присваивание");
            }
        }
        nextToken(TokenType.Close_Breaket, "Ожидался символ )");
        token = nextTokenRead();
        if (isOperatorsAndData(token))
            operatorsAndData();
        else
            printError("Ожидались операторы и данные");
    }

    private boolean isExpression (Token token) {
        return isExpression2(token);
    }

    private void expression () {
        expression2();
        Token token = nextTokenRead();
        while (token.getType() == TokenType.Eq || token.getType() == TokenType.Not_Eq) {
            scanner.nextScanner();
            expression2();
            token = nextTokenRead();
        }

    }

    private boolean isExpression2 (Token token) {
        return isExpression3(token);
    }

    private void expression2 () {
        expression3();
        Token token = nextTokenRead();
        while (token.getType() == TokenType.Great ||
                token.getType() == TokenType.Less ||
                token.getType() == TokenType.Great_Eq ||
                token.getType() == TokenType.Less_Eq) {
            scanner.nextScanner();
            expression3();
            token = nextTokenRead();
        }
    }

    // выражение 3
    private boolean isExpression3 (Token token) {
        return isExpression4(token);
    }

    private void expression3 () {
        expression4();
        Token token = nextTokenRead();
        while (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus) {
            scanner.nextScanner();
            expression4();
            token = nextTokenRead();
        }
    }

    // выражение 4
    private boolean isExpression4 (Token token) {
        return isExpression5(token);
    }

    private void expression4 () {
        expression5();
        Token token = nextTokenRead();
        while ( token.getType() == TokenType.Star ||
                token.getType() == TokenType.Slash ||
                token.getType() == TokenType.Percent ) {
            scanner.nextScanner();
            expression5();
            token = nextTokenRead();
        }
    }

    // выражение 5
    private boolean isExpression5 (Token token) {
        return isExpression6(token) ||
                token.getType() == TokenType.Plus ||
                token.getType() == TokenType.Minus;
    }

    private void expression5 () {

        Token token = nextTokenRead();
        while (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus) {
            scanner.nextScanner();
            token = nextTokenRead();
        }
        expression6();

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

    private void expression6 () {
        Token token = scanner.nextScanner();
        if (token.getType() == TokenType.Id) {
            token = nextTokenRead();
            if (token.getType() == TokenType.Open_Square) {
                nextToken(TokenType.Open_Square, "Ожидался символ [");
                nextToken(TokenType.Type_Dec_Int, "Ожидалось целое десятичное");
                nextToken(TokenType.Close_Square, "Ожидался символ ]");
            }
            else if (token.getType() == TokenType.Open_Breaket) {
                nextToken(TokenType.Open_Breaket, "Ожидался символ (");
                nextToken(TokenType.Close_Breaket, "Ожидался символ )");
            }

        }
        else if (token.getType() == TokenType.Open_Breaket) {
            token = nextTokenRead();
            if (isExpression(token)) {
                expression();
            }
            else {
                printError("Ожидалось выржение");
            }
            nextToken(TokenType.Close_Breaket, "Ожидался символ )");
        }
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
