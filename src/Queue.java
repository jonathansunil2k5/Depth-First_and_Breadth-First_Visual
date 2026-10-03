import java.awt.*;
import java.util.ArrayList;

public class Queue {
    ArrayList<Point> elements;

    public Queue () {
        elements = new ArrayList<Point>();
    }

    public void push(Point p) {
        elements.add(p);
    }

    public Point pull() {
        if (elements.isEmpty()) {
            return null;
        }

        Point returning = elements.getFirst();
        elements.removeFirst();
        return returning;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public Point peek() {
        return elements.getLast();
    }
}
