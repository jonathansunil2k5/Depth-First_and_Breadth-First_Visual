import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Grid {
    static Point destination = new Point(50, 30);
    static Point source = new Point(45, 45);

    Point currentPoint;
    Stack stack;
    Queue queue;

    boolean reachedDestination;

    int screenWidth, screenHeight;
    int gridWidth, gridHeight;

    int cellSize;
    BufferedImage image;

    Cell[][] cellGrid;
    double[][] heuristicGrid;

    public Grid (int screenWidth, int screenHeight, int cellSize) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.gridWidth = screenWidth/cellSize;
        this.gridHeight = screenHeight/cellSize;
        this.cellSize = cellSize;

        image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);

        cellGrid = new Cell[gridHeight][gridWidth];
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                cellGrid[y][x] = new Cell(x, y);
            }
        }

        heuristicGrid = new double[gridHeight][gridWidth];
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                heuristicGrid[y][x] = (Math.hypot(destination.x-x, destination.y-y));
            }
        }

        Random random = new Random();
        destination = new Point(random.nextInt(gridWidth), random.nextInt(gridHeight));
        source = new Point(random.nextInt(gridWidth), random.nextInt(gridHeight));
    }

    public boolean validPoint(int x, int y) {
        if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight && (!cellGrid[y][x].isWall || new Point(x, y).equals(destination))) return true;
        return false;
    }

    public void initDepthFirst() {
        this.currentPoint = source;
        this.stack = new Stack();
        this.cellGrid[source.y][source.x].seen = true;
    }

    public void initBreadthFirst() {
        this.currentPoint = source;
        this.queue = new Queue();
        this.cellGrid[source.y][source.x].seen = true;
    }

    public void updateBreadthFirst() {
        if (reachedDestination || queue == null) return;

        ArrayList<Point> neighbours = new ArrayList<>();
        for (int x = currentPoint.x - 1; x <= currentPoint.x + 1; x++) {
            for (int y = currentPoint.y - 1; y <= currentPoint.y + 1; y++) {

                Point observedPoint = new Point(x, y);
                if (validPoint(x, y) && !observedPoint.equals(currentPoint) && !cellGrid[observedPoint.y][observedPoint.x].seen) {
                    neighbours.add(observedPoint);
                }
            }
        }

        Collections.shuffle(neighbours);
        for (Point p : neighbours) {
            cellGrid[p.y][p.x].seen = true;
            cellGrid[p.y][p.x].parent = currentPoint;

            if (p.equals(destination)) {
                reachedDestination = true;
                Point tracePoint = destination;
                while (tracePoint != null) {
                    cellGrid[tracePoint.y][tracePoint.x].onPath = true;
                    tracePoint = cellGrid[tracePoint.y][tracePoint.x].parent;
                }
                return;
            }

            queue.push(p);
        }

        cellGrid[currentPoint.y][currentPoint.x].visited = true;

        if (queue.isEmpty()) return;

        currentPoint = queue.pull();

        if (currentPoint.equals(destination)) {
            reachedDestination = true;
            Point tracePoint = destination;
            while (tracePoint != null) {
                cellGrid[tracePoint.y][tracePoint.x].onPath = true;
                tracePoint = cellGrid[tracePoint.y][tracePoint.x].parent;
            }
        }
    }

    public void updateDepthFirst() {
        if (reachedDestination || stack == null) return;

        if (currentPoint.equals(destination)) {
            reachedDestination = true;
            Point tracePoint = destination;
            while (tracePoint != null) {
                cellGrid[tracePoint.y][tracePoint.x].onPath = true;
                tracePoint = cellGrid[tracePoint.y][tracePoint.x].parent;
            }
            return;
        }

        ArrayList<Point> neighbours = new ArrayList<>();
        for (int x = currentPoint.x - 1; x <= currentPoint.x + 1; x++) {
            for (int y = currentPoint.y - 1; y <= currentPoint.y + 1; y++) {

                Point observedPoint = new Point(x, y);
                if (validPoint(x, y) && !observedPoint.equals(currentPoint) && !cellGrid[observedPoint.y][observedPoint.x].seen) {
                    neighbours.add(observedPoint);
                }
            }
        }

        Collections.shuffle(neighbours);
        for (Point p : neighbours) {
            cellGrid[p.y][p.x].seen = true;
            cellGrid[p.y][p.x].parent = currentPoint;

            if (p.equals(destination)) {
                reachedDestination = true;
                Point tracePoint = destination;
                while (tracePoint != null) {
                    cellGrid[tracePoint.y][tracePoint.x].onPath = true;
                    tracePoint = cellGrid[tracePoint.y][tracePoint.x].parent;
                }
                return;
            }

            stack.push(p);
        }

        cellGrid[currentPoint.y][currentPoint.x].visited = true;

        if (stack.isEmpty()) return;

        currentPoint = stack.pop();
    }

    //inputs chunk coordinates to paint a chunk
    public void fillCell(Point p, Color color) {
        int pixelOffset = 0; //optional border around each chunk. Makes it easier to count

        for (int x = (p.x*cellSize) + pixelOffset; x < ((p.x*cellSize) + (cellSize-pixelOffset)); x++) {
            for (int y = (p.y*cellSize) + pixelOffset; y < ((p.y*cellSize) + (cellSize-pixelOffset)); y++) {
                image.setRGB(x, y, color.getRGB()); //Pixel coordinates
            }
        }
    }

    public BufferedImage redrawImage() {
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);
        g.dispose();

        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                if (cellGrid[y][x].isWall) fillCell(new Point(x, y), Color.DARK_GRAY);

                if (cellGrid[y][x].seen) fillCell(new Point(x, y), Color.LIGHT_GRAY);

                if (cellGrid[y][x].visited) fillCell(new Point(x, y), Color.GRAY);

                if (cellGrid[y][x].onPath) fillCell(new Point(x, y), Color.ORANGE);

                if (cellGrid[y][x].position.equals(Grid.source)) fillCell(new Point(x, y), Color.BLUE);

                if (cellGrid[y][x].position.equals(Grid.destination)) fillCell(new Point(x, y), Color.RED);
            }
        }

        return image;
    }


}
