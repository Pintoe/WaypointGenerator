import Shapes.Obstacle;
import Shapes.SquareObject;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


public class GraphPanel extends JPanel {

    private final ArrayList<Obstacle> allObstacles;

    public static final int FRAME_SIZE = 1080;
    // private static final int Y_OFFSET = 5;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw the rectangle here
        for (int i = 0; i < allObstacles.size(); i++) {

            Obstacle currentSquareObject = this.allObstacles.get(i);

            int xPosition = GraphPanel.inchesToSwingPosition(currentSquareObject.getPositionX());
            int yPosition = GraphPanel.inchesToSwingPosition(currentSquareObject.getPositionY());
            int size = GraphPanel.inchesToSwingPosition(currentSquareObject.getTotalSize());

            int[] transformedRectangleVector = GraphPanel.toCenterAnchorPoint(xPosition, yPosition, size);


            g.drawRect(
                transformedRectangleVector[0],
                GraphPanel.transformYComponentToCartesianCoordinate(transformedRectangleVector[1]),
                size,
                size
            );


        }
    }
    public static int[] toCenterAnchorPoint(int xPosition, int yPosition, int size) {
        int halfSize = size / 2;

        return new int[] {
            xPosition - halfSize,
             yPosition + halfSize
        };
    }

    public static int transformYComponentToCartesianCoordinate(int yPosition) {
        return FRAME_SIZE - yPosition;
    }
    private static int inchesToSwingPosition(double inches) {
        return (int) ((inches / Graph.FIELD_LENGTH) * GraphPanel.FRAME_SIZE);
    }
    public GraphPanel(ArrayList<Obstacle> allObstacles) {
        this.allObstacles = allObstacles;
    }
    // create the GUI explicitly on the Swing event thread
    public void createAndShowGui() {
        JFrame frame = new JFrame("Graph Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setVisible(true);
    }

}