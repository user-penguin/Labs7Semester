package diagram;

import interpreter.MyStack;
import object.type_element.*;
import object.ProgramTree;
import object.TypeData;
import object.TypeObject;
import parser.Scanner;
import parser.Token;
import parser.TokenType;

import java.util.Deque;
import java.util.LinkedList;

public class MyDiagram {
    private Scanner scanner;

    private ProgramTree tree;
    private ProgramTree thisTree;
    private Deque<ProgramTree> stack = new LinkedList<>();
    private MyStack myStack = new MyStack();

    private boolean interpreterFlag = false;

    public MyDiagram (Scanner scanner) {
        this.scanner = scanner;
    }

    public void program () {
        nextToken(TokenType.CLASS, "Ожидался класс");
        Token tokenClassMain = nextToken(TokenType.MAIN_CLASS, "Ожидался Main");
        addClassMain(tokenClassMain);
        nextToken(TokenType.Open_Braces, "Ожидался символ {");

        Token token = nextTokenRead();
        while (token.getType() != TokenType.Close_Braces && token.getType() != TokenType.EOF) {
            if (isFunctionMain(token))
                function();
            else if (isData(token))
                data();
            else
                printError("Неизвестный символ");
            token = nextTokenRead();
        }
        nextToken(TokenType.Close_Braces, "Ожидался символ }");
    }

    // добавление класса в дерево
    private void addClassMain (Token token) {
        tree = thisTree = new ProgramTree(token.getText());
        thisTree.setRight(Empty.createEmptyNode());
        thisTree = thisTree.right;
    }

    private boolean isFunctionMain(Token token) {
        return token.getType() == TokenType.VOID || token.getType() == TokenType.PUBLIC;
    }

    private void function() {
        Token token = scanner.nextScanner();

        Token tokenFunction;
        if (token.getType() == TokenType.VOID) {
            tokenFunction = nextToken(TokenType.Id, "Ожидался идентификатор");
        } else {
            // встретилась главная функция
            // todo проверить на единственность - дальше есть, нужно make some tests
            nextToken(TokenType.STATIC, "Ожидался static");
            nextToken(TokenType.VOID, "Ожидался void");
            tokenFunction = nextToken(TokenType.MAIN_FUNC, "Ожидался main");
        }
        addFunction(tokenFunction);

        nextToken(TokenType.Open_Breaket, "Ожидался символ (");
        nextToken(TokenType.Close_Breaket, "Ожидался символ )");

        token = nextTokenRead();
        if (isOperatorsAndData(token))
            operatorsAndData();
        else
            printError("Ожидался символ {");
    }

    private void addFunction(Token token) {
        if (thisTree.findUpFunction(token.getText()) != null) {
            printSemError("Функция " + token.getText() + " уже была объявлена");
        }
        thisTree.setLeft(Function.createFunction(token.getText()));
        thisTree = thisTree.left;
    }

    private boolean isOperatorsAndData (Token token) {
        return token.getType() == TokenType.Open_Braces;
    }

    private void operatorsAndData () {
        nextToken(TokenType.Open_Braces, "Ожидался символ {");
        in();
        Token token = nextTokenRead();
        boolean find = false;
        // тут была открывающаяся скобка
        while (token.getType() != TokenType.Close_Braces && token.getType() != TokenType.EOF) {
            if (isOperator(token)) {
                find = true;
                operator();
            } else if (isData(token)) {
                find = true;
                data();
            } else {
                printError("Неизвестный символ");
            }
            token = nextTokenRead();
        }
        if (!find) {
            printError("Недостижимый код");
        }
        out();
        nextToken(TokenType.Close_Braces, "Ожидался символ }");
    }

    private void in () {
        stack.push(thisTree);
        thisTree.setRight(Empty.createEmptyNode());
        thisTree = thisTree.right;
    }

    private void out () {
        thisTree = stack.pop();
        if (thisTree.node.getTypeObject() == TypeObject.EMPTY) {
            thisTree.setLeft(Empty.createEmptyNode());
            thisTree = thisTree.left;
        }
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
        } else {
            typeData = TypeData.DOUBLE;
        }

        token = nextTokenRead();
        if (isVariable(token)) {
            variable(typeData);
        } else {
            printError("Ожидался идентификатор");
        }

        token = nextTokenRead();
        while (token.getType() == TokenType.Comma) {
            scanner.nextScanner();
            token = nextTokenRead();
            if (isVariable(token)) {
                variable(typeData);
            } else {
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
        // запоминаем имя переменной
        Token varName = nextToken(TokenType.Id, "Ожидался ID");

        // сбда упадёт узел после вычислений
        Node result = null;

        boolean init = false;
        Token token = nextTokenRead();
        // если просто переменная
        if (token.getType() == TokenType.Assign) {
            nextToken(TokenType.Assign, "Ожидался символ =");
            token = nextTokenRead();
            if (isExpression(token)) {
                // получаем узел с посчитанным выражением
                result = expression();
                // если вдруг вернулось давловское в интовое, вернуть ошибку, так завещала java
                if ((typeData == TypeData.CHAR || typeData == TypeData.INTEGER)
                        && result.typeData == TypeData.DOUBLE) {
                    printError("Ошибка приведения типов, double недопустим");
                }
            } else {
                printError("Ожидалось выражение");
            }
            // если после присваивания
            init = true;


            // если массив
        } else if (token.getType() == TokenType.Open_Square) {
            if (thisTree.findUpArray(varName.getText()) != null) {
                printError("Массив с именем " + varName + " уже объявлен");
            }
            nextToken(TokenType.Open_Square, "Ожидался символ [");
            nextToken(TokenType.Close_Square, "Ожидался символ ]");
            if (thisTree.findUpVarOrArray(varName.getText()) != null) {
                printSemError("Идентификатор " + varName.getText() + " уже использовался");
            }
            if (nextTokenRead().getType() == TokenType.Assign) {
                nextToken(TokenType.Assign, "Ожидался символ =");
                nextToken(TokenType.NEW, "Ожидался символ new");
                token = scanner.nextScanner();

                TypeData typeDataOfArray;
                if (token.getType() != TokenType.INT && token.getType() != TokenType.DOUBLE) {
                    printError("Ожидался тип");
                }
                if (tokenTypeToTypeData(token.getType()) != typeData) {
                    printError("Найдено несоответствие типов массива");
                }
                // запоминаем тип массива
                typeDataOfArray = tokenTypeToTypeData(token.getType());

                nextToken(TokenType.Open_Square, "Ожидался символ [");

                // здесь нужно посмотреть, стоит ли там целочисленная константа или интовая переменная
                token = nextTokenRead();
                if (isExpression(token)) {
                    Node node = expression();
                    // что-то получили, теперь нужно проанализировать
                    if (!node.typeData.equals(TypeData.INTEGER)) {
                        printError("Ожидалось целочисленное выражение");
                    }
                    if (node.value_int <= 0) {
                        printError("Размера массива не может быть меньше единицы");
                    }
                    else {
                        init = true;
                        result = node;
                    }
                }


                nextToken(TokenType.Close_Square, "Ожидался символ ]");
                if (typeData == typeDataOfArray) {
                    addArray(typeData, true, varName, result.value_int);
                } else {
                    printSemError("Неверный тип массива");
                }
            } else {
                addArray(typeData, false, varName, 0);
            }
            return;
        }
        addVar(typeData, init, varName, result);
    }

    // перевод TokenType в TypeData
    private TypeData tokenTypeToTypeData(TokenType tokenType) {
        if (tokenType.equals(TokenType.INT)) {
            return TypeData.INTEGER;
        }
        if (tokenType.equals(TokenType.DOUBLE)) {
            return TypeData.DOUBLE;
        }
        return TypeData.UNKNOW;
    }

    // добавление массива
    private void addArray (TypeData typeData, boolean init, Token name, int n) {
        if (thisTree.findUpArray(name.getText()) != null) {
            printSemError("Массив " + name.getText() + " уже существует");
        }
        else {
            thisTree.setLeft(Array.createArray(name.getText(), typeData, n));
            thisTree.left.node.isInit = init;
            thisTree = thisTree.left;
        }
    }

    // добавление переменной
    private void addVar (TypeData typeData, boolean init, Token name, Node node) {
        if (thisTree.findUpVar(name.getText()) != null) {
            printSemError("Переменная " + name.getText() + " уже существует");
        } else {
            thisTree.setLeft(Variable.createVar(name.getText(), typeData, node));
            thisTree.left.node.isInit = init;
            thisTree = thisTree.left;
        }
    }

    private boolean isOperator(Token token) {
        return token.getType() == TokenType.Semicolon ||
                isOperatorsAndData(token) ||
                isLoopFor(token) ||
                token.getType() == TokenType.Id;
    }

    // метод присваивания переменной нового значения
    private void changeValueByAssignment(Node oldNode, Node newNode) {
        if (isType(oldNode.typeData, newNode.typeData)) {
            // старый дабл
            if (oldNode.typeData.equals(TypeData.DOUBLE)) {
                if (newNode.typeData.equals(TypeData.DOUBLE)) {
                    oldNode.value_double = newNode.value_double;
                } else if (newNode.typeData.equals(TypeData.INTEGER)) {
                    oldNode.value_double = (double) newNode.value_int;
                } else {
                    printError("Какой-то гайдай вернулся при присваивании к даблу");
                }
            } else if (oldNode.typeData.equals(TypeData.INTEGER)) {
                if (newNode.typeData.equals(TypeData.INTEGER)) {
                    oldNode.value_int = newNode.value_int;
                } else {
                    printError("В старый интежер попытались присунуть какой-то не интовый тип, плачет интерпр-р по приведению");
                }
            }
        } else {
            printError("Возникли неприводимые типы");
        }
    }

    // проверка на существование переменной
    private boolean isVarExist(String name) {
        if (thisTree.findUpVar(name) != null) {
            return true;
        }
        return false;
    }

    private void operator() {
        Token token = nextTokenRead();
        // todo работа с операторами
        if (token.getType() == TokenType.Semicolon) {
            nextToken(TokenType.Semicolon, "Ожидался символ ;");
        } else if (isOperatorsAndData(token)) {
            operatorsAndData();
        } else if (isLoopFor(token)) {
            loopFor();
            // если встретился идентификатор в начале строки
        } else if (token.getType() == TokenType.Id) {
            Token tokenName = nextToken(TokenType.Id, "Ожидался идентификатор");
            token = nextTokenRead();

            // происходит присваивание переменной нового значения
            if (isAssignment(token)) {
                Node node = assignment(tokenName);
                // проверка на существование переменной
                if (isVarExist(tokenName.getText())) {
                    changeValueByAssignment(thisTree.findUpVar(tokenName.getText()).node, node);
                } else {
                    printError("Переменной " + tokenName.getText() + " не существует");
                }
                changeValueByAssignment(thisTree.findUpVar(tokenName.getText()).node, node);
            } else if (token.getType() == TokenType.Open_Breaket) {
                nextToken(TokenType.Open_Breaket, "Ожидался символ (");
                if (thisTree.findUpFunction(tokenName.getText()) == null) {
                    printSemError("Функция '" + tokenName.getText() + "()' не определена");
                }
                nextToken(TokenType.Close_Breaket, "Ожидался символ )");
                nextToken(TokenType.Semicolon, "Ожидался символ ;");
            } else if (token.getType() == TokenType.Open_Square) {
                nextToken(TokenType.Open_Square, "Ожидался символ [");
                // достаём номер элемента в массиве =========================
                Token indexArray = nextToken(TokenType.INT, "Ожидалось целое");

                if (thisTree.findUpArray(tokenName.getText()) != null) {
                    Node mass = thisTree.findUpArray(tokenName.getText()).node;
                    if (mass.n <= Integer.parseInt(indexArray.getText())) {
                        printSemError("Выход за границу массива");
                    }
                }

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

    private Node assignment(Token tokenName) {
        nextToken(TokenType.Assign, "Ожидался символ =");
        Token token = nextTokenRead();
        if (isExpression(token)) {
            Node node = expression();
            // ищем, есть ли чему присваивать
            if (thisTree.findUpVarOrArray(tokenName.getText()) == null) {
                printSemError("Переменная или массив '" + tokenName.getText() + "' не найдены");
            } else {
                // если нашли
                if (isType(thisTree.findUpVarOrArray(tokenName.getText()).node.typeData, node.typeData)) {
                    Node mass = thisTree.findUpVarOrArray(tokenName.getText()).node;
                    mass.isInit = true;
                } else {
                    printSemError("Не верный тип");
                }
            }
            return node;
        } else  {
            nextToken(TokenType.NEW, "Ожидался new");
            token = scanner.nextScanner();
            TokenType typeMass =  token.getType();
            if (typeMass != TokenType.DOUBLE && typeMass != TokenType.INT)
                printError("Ожидался тип");
            nextToken(TokenType.Open_Square, "Ожидался символ [");

            Token tokenN = nextToken(TokenType.INT, "Ожидалось целое");

            if (thisTree.findUpArray(tokenName.getText()).node.typeData == null) {
                printSemError("Массив '" + tokenName.getText() + "' не найден");
            } else {
                TypeData typeDataMass;
                if (typeMass == TokenType.DOUBLE) {
                    typeDataMass = TypeData.DOUBLE;
                } else {
                    typeDataMass = TypeData.INTEGER;
                }
                if (thisTree.findUpArray(tokenName.getText()).node.typeData == typeDataMass) {
                    Node mass = thisTree.findUpVarOrArray(tokenName.getText()).node;
                    mass.isInit = true;
                    mass.n = Integer.parseInt(tokenN.getText());
                } else {
                    printSemError("Не верный тип массива");
                }
            }
            nextToken(TokenType.Close_Square, "Ожидался символ ]");
            return  Array.createArray("", TypeData.DOUBLE, 3);
        }
    }

    private boolean isLoopFor (Token token) {
        return token.getType() == TokenType.FOR;
    }

    // todo научиться интерпретировать форик
    private void loopFor() {
        nextToken(TokenType.FOR, "Ожидался for");
        nextToken(TokenType.Open_Breaket, "Ожидался символ (");
        Token token = nextTokenRead();
        TypeData typeData;
        if (token.getType() == TokenType.INT || token.getType() == TokenType.DOUBLE) {
            if (token.getType() == TokenType.INT) {
                typeData = TypeData.INTEGER;
                nextToken(TokenType.INT, "Ожидался int");
            } else {
                typeData = TypeData.DOUBLE;
                nextToken(TokenType.DOUBLE, "Ожидался double");
            }
            token = nextTokenRead();
            if (isVariable(token)) {
                variable(typeData);
            } else {
                nextToken(TokenType.Id, "Ожидался идентификатор");
            }
        } else if (token.getType() == TokenType.Id) {
            Token tokenName = nextToken(TokenType.Id, "Ожидался идентификатор");
            token = nextTokenRead();
            if (isAssignment(token)) {
                Node node = assignment(tokenName);
            }
        } else {
            printError("Ожидалось выражение с присваиванием");
        }
        nextToken(TokenType.Semicolon, "Ожидался символ ;");

        // серединка фора
        token = nextTokenRead();
        if (isExpression(token)) {
            Node node = expression();
            if (node.typeData != TypeData.INTEGER)
                printSemError("Ожидался INT");
        }
        else {
            printError("Ожидалось выражение");
        }

        nextToken(TokenType.Semicolon, "Ожидался символ ;");

        // третья часть
        token = nextTokenRead();
        if (token.getType() == TokenType.Id) {
            nextToken(TokenType.Id, "Ожидался идентификатор");
            if (nextTokenRead().getType() == TokenType.Assign) {
                nextToken(TokenType.Assign, "Ожидалось присваивание");
                if (isExpression(nextTokenRead())) {
                    expression();
                }
            }
        } else {
            printError("Ожидалось присваивание");
        }
        nextToken(TokenType.Close_Breaket, "Ожидался символ )");
        token = nextTokenRead();
        if (isOperatorsAndData(token)) {
            operatorsAndData();
        } else if (isOperator(token)) {
            operator();
        } else {
            printError("Ожидались операторы и данные");
        }
    }

    private boolean isExpression (Token token) {
        return isExpression2(token);
    }

    private Node expression () {
        Node node = expression2();
        TypeData typeData = node.typeData;

        Token token = nextTokenRead();
        while (token.getType() == TokenType.Eq || token.getType() == TokenType.Not_Eq) {
            scanner.nextScanner();

            Node node2 = expression2();
            TypeData typeData2 = node2.typeData;
            if (isNumber(typeData) && isNumber(typeData2)) {
                node.typeData = TypeData.INTEGER;
            } else {
                node.typeData = TypeData.UNKNOW;
                printSemError("Неопределённый тип");
            }
            token = nextTokenRead();
        }
        return node;

    }

    private boolean isExpression2 (Token token) {
        return isExpression3(token);
    }

    private Node expression2 () {
        Node node = expression3();
        TypeData typeData = node.typeData;

        Token token = nextTokenRead();
        while (token.getType() == TokenType.Great ||
                token.getType() == TokenType.Less ||
                token.getType() == TokenType.Great_Eq ||
                token.getType() == TokenType.Less_Eq) {
            scanner.nextScanner();

            Node node2 = expression3();
            TypeData typeData2 = node2.typeData;
            if (isNumber(typeData) && isNumber(typeData2)) {
                node.typeData = TypeData.INTEGER;
            } else {
                node.typeData = TypeData.UNKNOW;
                printSemError("Неопределённый тип");
            }
            token = nextTokenRead();
        }
        return node;
    }

    // выражение 3
    private boolean isExpression3 (Token token) {
        return isExpression4(token);
    }

    private Node expression3 () {
        Node node = expression4();
        TypeData typeData = node.typeData;

        Token token = nextTokenRead();
        while (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus) {
            scanner.nextScanner();

            Node node2 = expression4();
            TypeData typeData2 = node2.typeData;

            node.typeData = toTypeDataPlusMinus(typeData, typeData2);
            if (typeData ==TypeData.UNKNOW) {
                printSemError("Неопределённый тип");
            }
            token = nextTokenRead();
        }
        return node;
    }

    private TypeData toTypeDataPlusMinus (TypeData typeData1, TypeData typeData2) {
        return toTypeDataSlashStar(typeData1, typeData2);
    }

    private TypeData toTypeDataSlashStar(TypeData typeData1, TypeData typeData2) {
        if (typeData1 == TypeData.DOUBLE || typeData2 == TypeData.DOUBLE) {
            if (isNumber(typeData1) && isNumber(typeData2)) {
                return TypeData.DOUBLE;
            } else {
                return TypeData.UNKNOW;
            }
        } else if (typeData1 == TypeData.INTEGER || typeData2 == TypeData.INTEGER) {
            if (isNumber(typeData1) && isNumber(typeData2)) {
                return TypeData.INTEGER;
            } else {
                return TypeData.UNKNOW;
            }
        } else if (typeData1 == TypeData.INT16 && typeData2 == TypeData.INT16) {
            if (isNumber(typeData1) && isNumber(typeData2)) {
                return TypeData.INTEGER;
            } else {
                return TypeData.UNKNOW;
            }
        } else if (typeData1 == TypeData.INT8 && typeData2 == TypeData.INT8) {
            if (isNumber(typeData1) && isNumber(typeData2)) {
                return TypeData.INTEGER;
            } else {
                return TypeData.UNKNOW;
            }
        } else if (typeData1 == TypeData.INT8 && typeData2 == TypeData.INT16 ||
                typeData1 == TypeData.INT16 && typeData2 == TypeData.INT8) {
            if (isNumber(typeData1) && isNumber(typeData2)) {
                return TypeData.INTEGER;
            }
            else {
                return TypeData.UNKNOW;
            }
        }  else {
            return TypeData.UNKNOW;
        }
    }

    private TypeData toTypeDataPercent(TypeData typeData1, TypeData typeData2) {
        if (isNumber(typeData1) && (
                typeData2 == TypeData.INTEGER ||
                typeData2 == TypeData.INT8 ||
                typeData2 == TypeData.INT16)
        ) {
            return TypeData.INTEGER;
        } else
            return TypeData.UNKNOW;
    }


    // выражение 4
    private boolean isExpression4 (Token token) {
        return isExpression5(token);
    }

    private Node expression4 () {
        Node node = expression5();

        Token token = nextTokenRead();
        while ( token.getType() == TokenType.Star ||
                token.getType() == TokenType.Slash ||
                token.getType() == TokenType.Percent ) {
            scanner.nextScanner();
            Node node2 = expression5();
            TypeData typeData2 = node2.typeData;
            if (token.getType() == TokenType.Percent) {
                node.typeData = toTypeDataPercent(node.typeData, typeData2);
            } else {
                node.typeData = toTypeDataSlashStar(node.typeData, typeData2);
            }
            if (node.typeData == TypeData.UNKNOW) {
                printSemError("Неопределённый тип");
            }
            token = nextTokenRead();
        }
        return node;
    }

    // выражение 5
    private boolean isExpression5 (Token token) {
        return isExpression6(token) ||
                token.getType() == TokenType.Plus ||
                token.getType() == TokenType.Minus;
    }

    private Node expression5 () {
        Token token = nextTokenRead();

        boolean isZnak = false;
        int sign = 1;
        while (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus) {
            scanner.nextScanner();
            token = nextTokenRead();
            isZnak = true;
            sign *= -1;
        }

        Node node = expression6();
        if (isZnak) {
            if (node.typeData != TypeData.DOUBLE && node.typeData != TypeData.INTEGER) {
                printSemError("Неопределённый тип");
                return Unknown.createUnknow();
            } else {
                return node;
            }
        } else {
            return node;
        }
    }

    // выражение 6
    private boolean isExpression6 (Token token) {
        return token.getType() == TokenType.Id ||
                token.getType() == TokenType.INT ||
                token.getType() == TokenType.INT16 ||
                token.getType() == TokenType.INT8 ||
                token.getType() == TokenType.Const_Char;
    }

    private Node expression6 () {
        Token token = scanner.nextScanner();

        if (token.getType() == TokenType.Id) {
            Token tokenName = token;
            token = nextTokenRead();
            // если впереди массив []
            if (token.getType() == TokenType.Open_Square) {
                nextToken(TokenType.Open_Square, "Ожидался символ [");
                token = nextTokenRead();
                if (isExpression(token)) {
                    Node node = expression();
                } else {
                    // выражение или инт-константа
                    nextToken(TokenType.INT, "Ожидалось целое");
                }
                nextToken(TokenType.Close_Square, "Ожидался символ ]");
                if (thisTree.findUpVarOrArray(tokenName.getText()) != null)
                    return Const.createConst(thisTree.findUpVarOrArray(tokenName.getText()).node.typeData);
                else {
                    printSemError("Неизвестная переменная");
                    return Unknown.createUnknow();
                }
            } else {
                if (thisTree.findUpVarOrArray(tokenName.getText()) != null) {
                    // если переменная
                    //return Node.createConst(thisTree.findUpVarOrArray(tokenName.getText()).node.typeData);
                    return thisTree.findUpVarOrArray(tokenName.getText()).node;
                }
                else {
                    printSemError("Неизвестная переменная");
                    return Unknown.createUnknow();
                }
            }
        } else if (token.getType() == TokenType.Open_Breaket) {

            token = nextTokenRead();
            Node node = null;
            if (isExpression(token))
                node = expression();
            else
                printError("Ожидалось выражение");
            nextToken(TokenType.Close_Breaket, "Ожидался символ )");
            return node;
        } else if (token.getType() == TokenType.INT) {
            Const node = new Const();
            node.value_int = Integer.parseInt(token.getText());
            node.isInit = true;
            // вопросики псц
            node.typeObject = TypeObject.CONST;
            node.typeData = TypeData.INTEGER;
            return node;
//            return Node.createConst(TypeData.INTEGER);
        } else if (token.getType() == TokenType.DOUBLE) {
            Const node = new Const();
            node.value_double = Double.parseDouble(token.getText());
            node.isInit = true;
            node.typeData = TypeData.DOUBLE;
            // вопросики опять же
            node.typeObject = TypeObject.CONST;

            return node;
//            return Node.createConst(TypeData.DOUBLE);
        }  else {
            Const node = new Const();
            node.value_int = token.getText().charAt(0);
            node.isInit = true;
            node.typeData = TypeData.CHAR;
            // и тут косяк, но разберёмся попозже
            node.typeObject = TypeObject.CONST;

            return node;
//            return Node.createConst(TypeData.CHAR);
        }
    }

    private Boolean isNumber(TypeData typeData) {
        return typeData == TypeData.DOUBLE ||
                typeData == TypeData.INTEGER ||
                typeData == TypeData.INT8 ||
                typeData == TypeData.INT16;
    }

    private boolean isType(TypeData typeData1, TypeData typeData2) {
        if (typeData1 == TypeData.DOUBLE && isNumber(typeData2)) {
            return true;
        }
        if (typeData1 == TypeData.INTEGER && isNumber(typeData2) && typeData2 != TypeData.DOUBLE) {
            return true;
        }
        if (typeData1 == TypeData.INTEGER && typeData2 == TypeData.INTEGER) {
            return true;
        }
        // char приведение может быть размещено здесь
        return false;
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
    public ProgramTree getTree() {
        return tree;
    }
}
