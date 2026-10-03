import java.awt.*;
import java.util.ArrayList;

public class Stack {
    ArrayList<Point> elements;

    public Stack () {
        elements = new ArrayList<Point>();
    }

    public void push(Point p) {
        elements.add(p);
    }

    public Point pop() {
        if (elements.isEmpty()) {
            return null;
        }

        Point returning = elements.getLast();
        elements.removeLast();
        return returning;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public Point peek() {
        return elements.getLast();
    }
}
