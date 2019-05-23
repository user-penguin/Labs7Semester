package object.type_element;

import object.TypeData;
import object.TypeObject;

public abstract class Node implements INode {
    public String name;
    public TypeObject typeObject;
    public TypeData typeData;
    public int n;
    private Variable[] arrayElements;

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

    @Override
    public abstract String toString();
}
