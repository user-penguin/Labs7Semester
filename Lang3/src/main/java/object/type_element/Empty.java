package object.type_element;

import object.TypeObject;

public class Empty extends Node {

    public static Node createEmptyNode() {
        Empty node = new Empty();
        node.typeObject = TypeObject.EMPTY;
        return node;
    }

    @Override
    public String toString() {
        return this.typeObject.toString();
    }
}
