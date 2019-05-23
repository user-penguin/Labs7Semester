package object.type_element;

import object.TypeData;
import object.TypeObject;

public class Variable extends Node {

    public static Node createVar(String name, TypeData typeData) {
        Variable node = new Variable();
        node.typeObject = TypeObject.VAR;
        node.name = name;
        node.typeData = typeData;
        return node;
    }

    @Override
    public String toString() {
        String str = this.typeObject.toString();
        str += " init = " + this.isInit;
        str += " name = " + this.name;
        if (this.isInit) {
            str += " value = " + getValue();
        }
        return str;
    }
}
