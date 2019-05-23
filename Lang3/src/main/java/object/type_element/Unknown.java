package object.type_element;

import object.TypeData;
import object.TypeObject;

public class Unknown extends Node {

    public static Node createUnknow() {
        Unknown node = new Unknown();
        node.typeObject = TypeObject.CONST;
        node.typeData = TypeData.UNKNOW;
        return node;
    }

    @Override
    public String toString() {
        return typeObject.toString();
    }
}
