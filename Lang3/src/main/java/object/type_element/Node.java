package object.type_element;

import object.TypeData;
import object.TypeObject;

public abstract class Node implements INode {
    String name;
    public TypeObject typeObject;
    public TypeData typeData;

    public boolean isInit;

    public int value_int;
    public double value_double;
    public char value_char;

    public Node() {
        isInit = false;
    }

    public TypeObject getTypeObject () {
        return typeObject;
    }

    public static Node createVar(String name, TypeData typeData) {
        Variable node = new Variable();
        node.typeObject = TypeObject.VAR;
        node.name = name;
        node.typeData = typeData;
        return node;
    }

    public static Node createArray(String name, TypeData typeData, int n) {
        Array node = new Array();
        node.typeObject = TypeObject.ARRAY;
        node.name = name;
        node.typeData = typeData;
        node.n = n;
        return node;
    }

    public static Node createFunction(String name) {
        Function node = new Function();
        node.typeObject = TypeObject.FUNCTION;
        node.name = name;
        node.typeData = TypeData.VOID;
        return node;
    }

    static Node createClass(String name) {
        Class node = new Class();
        node.typeObject = TypeObject.CLASS;
        node.name = name;
        node.typeData = TypeData.VOID;
        return node;
    }

    public static Node createEmptyNode() {
        Empty node = new Empty();
        node.typeObject = TypeObject.EMPTY;
        return node;
    }

    public static Node createConst(TypeData typeData) {
        Const node = new Const();
        node.typeObject = TypeObject.CONST;
        node.typeData = typeData;
        return node;
    }

    public static Node createUnknow() {
        Unknown node = new Unknown();
        node.typeObject = TypeObject.CONST;
        node.typeData = TypeData.UNKNOW;
        return node;
    }

    @Override
    public String toString() {
        if (typeObject == TypeObject.EMPTY)
            return typeObject.toString();

        String str = typeObject.toString();
        if (typeObject != TypeObject.CLASS)
            str += " " + typeData;
        str += " " + name;
        if (typeObject == TypeObject.ARRAY)
            str += " n=" + n;
        if (typeObject != TypeObject.CLASS && typeObject != TypeObject.FUNCTION)
            str += " init-" + isInit;
        return str;
    }
}
