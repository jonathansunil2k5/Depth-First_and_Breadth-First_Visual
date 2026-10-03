import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    int screenWidth, screenHeight;
    Grid grid;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(grid.redrawImage(), 0, 0, null);
    }

    public Renderer(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.grid = new Grid(screenWidth, screenHeight, 8);
    }

    public static void main(String[] args) {
        int screenWidth = 720;
        int screenHeight = 720;

        JFrame frame = new JFrame("");
        Renderer panel = new Renderer(screenWidth, screenHeight);

        frame.setSize(screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

//        panel.grid.initDepthFirst();
        panel.grid.initBreadthFirst();
        new Timer(2, e -> {
            panel.repaint();
//            panel.grid.updateDepthFirst();
            panel.grid.updateBreadthFirst();
        }).start();
    }
}
