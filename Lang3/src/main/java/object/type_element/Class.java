package object.type_element;

import object.TypeData;
import object.TypeObject;

public class Class extends Node {

    public static Node createClass(String name) {
        Class node = new Class();
        node.typeObject = TypeObject.CLASS;
        node.name = name;
        node.typeData = TypeData.VOID;
        return node;
    }

    @Override
    public String toString() {
        String str = typeObject.toString();
        str += " " + this.typeData;
        return str;
    }
}
