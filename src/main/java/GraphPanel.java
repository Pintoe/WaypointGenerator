import Shapes.Obstacle;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GraphPanel extends JPanel {

    private final ArrayList<Obstacle> allObstacles;

    public static final int FRAME_SIZE = 1080;
    // private static final int Y_OFFSET = 5;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("C:\\Users\\darre\\Documents\\Code\\Java\\WaypointGenerator\\src\\main\\resources\\powerplay-field.png")), 0, 0, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Graphics2D newGraphicsObject = (Graphics2D) g.create();

        newGraphicsObject.setPaint(new Color(255, 0, 0, 128));
        for (int i = 0; i < allObstacles.size(); i++) {
            Obstacle currentObject = this.allObstacles.get(i);

            int xPosition = GraphPanel.inchesToSwingPosition(currentObject.getPositionX());
            int yPosition = GraphPanel.inchesToSwingPosition(currentObject.getPositionY());
            int size = GraphPanel.inchesToSwingPosition(currentObject.getTotalSize());

            int[] transformedRectangleVector = GraphPanel.toCenterAnchorPoint(xPosition, yPosition, size);

            switch (currentObject.getShape()) {
                case SQUARE:
                    newGraphicsObject.fill(
                            new Rectangle(
                                transformedRectangleVector[0],
                                GraphPanel.transformYComponentToCartesianCoordinate(transformedRectangleVector[1]),
                                size,
                                size
                            )
                    );

                case CIRCLE: {
                    Ellipse2D newCircle = new Ellipse2D.Double(
                        transformedRectangleVector[0],
                        GraphPanel.transformYComponentToCartesianCoordinate(transformedRectangleVector[1]),
                        size,
                        size
                    );


                    newGraphicsObject.fill(
                        newCircle
                    );
                }
            }


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