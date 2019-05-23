package object.type_element;

import object.TypeData;
import object.TypeObject;

public class Const extends Node {

    public static Node createConst(TypeData typeData) {
        Const node = new Const();
        node.typeObject = TypeObject.CONST;
        node.typeData = typeData;
        return node;
    }

    @Override
    public String toString() {
        return this.typeObject.toString();
    }
}
