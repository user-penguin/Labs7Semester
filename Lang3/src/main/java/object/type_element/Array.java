package object.type_element;

import object.TypeData;
import object.TypeObject;

public class Array extends Node {
    public int n;
    private Variable[] arrayElements;

    public static Array createArray(String name, TypeData typeData, int n) {
        Array node = new Array();
        node.typeObject = TypeObject.ARRAY;
        node.name = name;
        node.typeData = typeData;
        node.n = n;
        return node;
    }

    @Override
    public String toString() {
        String str = typeObject.toString();
        str += " init = " + this.isInit;
        str += " size = " + this.n;
        str += " type = " + this.typeData;
        return str;
    }
}
