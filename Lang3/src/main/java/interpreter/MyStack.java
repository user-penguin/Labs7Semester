package interpreter;

import object.Node;

import java.util.ArrayList;
import java.util.List;

public class MyStack {
    List<Node> body;

    public MyStack() {
        body = new ArrayList<>();
    }

    public Node pop() {
        if (body.size() > 0) {
            Node node = body.get(0);
            body.remove(0);
            return node;
        }
        return null;
    }

    public void push(Node node) {
        body.add(0, node);
    }

    public Node getHead() {
        return body.get(0);
    }
}
