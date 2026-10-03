import java.awt.*;
import java.util.Random;

public class Cell {
    Point position, parent;
    boolean visited, seen, onPath;
    boolean isWall;

    public Cell (int x, int y) {
        Random random = new Random();

        this.position = new Point(x, y);

        if (this.position.equals(Grid.destination)) this.isWall = false;
        else this.isWall = random.nextInt(3) == 1;
    }
}
