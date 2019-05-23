package object.type_element;

import object.TypeData;
import object.TypeObject;

public class Function extends Node {

    public static Node createFunction(String name) {
        Function node = new Function();
        node.typeObject = TypeObject.FUNCTION;
        node.name = name;
        node.typeData = TypeData.VOID;
        return node;
    }

    @Override
    public String toString() {
        String str = this.typeObject.toString();
        str += ' ' + this.name;
        return str;
    }
}
