package object.type_element;

import object.TypeData;
import object.TypeObject;

public class Variable extends Node {

    public static Node createVar(String name, TypeData typeData, Node extNode) {
        // todo запилить заполнение значения, если оно есть
        Variable node = new Variable();
        node.typeObject = TypeObject.VAR;
        node.name = name;
        node.typeData = typeData;
        if (extNode == null) {
            return node;
        }
        if (typeData.equals(TypeData.INTEGER)) {
            node.value_int = extNode.value_int;
        } else {
            if (typeData.equals(TypeData.DOUBLE)) {
                node.value_double = extNode.value_double;
            } else {
                if (typeData.equals(TypeData.CHAR)) {
                    node.value_char = extNode.value_char;
                }
            }
        }

        return node;
    }

    @Override
    public String toString() {
        String str = this.typeObject.toString();
        str += " init = " + this.isInit;
        str += " name = " + this.name;
        str += " type = " + this.typeData;
        if (this.isInit) {
            str += " value = " + getValue();
        }
        return str;
    }

    private String getValue() {
        if (this.typeData.equals(TypeData.INTEGER)) {
            return String.valueOf(this.value_int);
        }
        if (this.typeData.equals(TypeData.DOUBLE)) {
            return String.valueOf(this.value_double);
        }
        if (this.typeData.equals(TypeData.CHAR)) {
            return String.valueOf(this.value_char);
        }
        return "Значения не найдено";
    }
}
