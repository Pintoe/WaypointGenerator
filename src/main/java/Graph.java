import Shapes.Obstacle;
import Shapes.SquareObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

import javax.swing.*;

public class Graph {
    public static final int FIELD_LENGTH = 144; // inches
    private static final double UNIT_LENGTH = 0.5D; // inches
    private static final int TOTAL_AMOUNT_OF_UNITS = (int) (FIELD_LENGTH / UNIT_LENGTH);

    private static final int ROBOT_SIZE = 18;
    private final boolean[][] adjacencyMatrix;

    ArrayList<Obstacle> allObjects = new ArrayList<>();
    public Graph() {
        this.adjacencyMatrix = new boolean[TOTAL_AMOUNT_OF_UNITS][TOTAL_AMOUNT_OF_UNITS];

        for (int i = 0; i < TOTAL_AMOUNT_OF_UNITS; i++) {
            for (int j = 0; j < TOTAL_AMOUNT_OF_UNITS; j++) {
                this.adjacencyMatrix[i][j] = true;
            }
        }
    }

    public void addObstacle(SquareObject newObject) {
        int amountOfSquaresObstructedX = (int) (newObject.getTotalSize() / UNIT_LENGTH);

        int middleXIndex = getIndexFromPosition(newObject.getPositionX());
        int middleYIndex = getIndexFromPosition(newObject.getPositionY());

        int squaresInBetween = (int) ((newObject.getTotalSize() / 2) / UNIT_LENGTH);

        int[] xBounds = new int[] {
                Math.max(middleXIndex - squaresInBetween, 0),
                Math.min(middleXIndex + squaresInBetween, TOTAL_AMOUNT_OF_UNITS )
        };

        int[] yBounds = new int[] {
                Math.max(middleYIndex - squaresInBetween, 0),
                Math.min(middleYIndex + squaresInBetween, TOTAL_AMOUNT_OF_UNITS)
        };

        for (int startYIndex = yBounds[0]; startYIndex < yBounds[1]; startYIndex++) {
            for (int startXIndex = xBounds[0]; startXIndex < xBounds[1]; startXIndex++) {
                this.adjacencyMatrix[startYIndex][startXIndex] = false;
            }
        }

    }

    public void addPowerPlayFieldObstacles() {

/*        SquareObject[] floorJunctions = new SquareObject[] {
                new SquareObject(24, 24, ROBOT_SIZE),
                new SquareObject(72, 24, ROBOT_SIZE),
                new SquareObject(120, 24, ROBOT_SIZE),
                new SquareObject(24, 72, ROBOT_SIZE),
                new SquareObject(72, 72, ROBOT_SIZE),
                new SquareObject(120, 72, ROBOT_SIZE),
                new SquareObject(24, 120, ROBOT_SIZE),
                new SquareObject(70, 120, ROBOT_SIZE),
                new SquareObject(120, 120, ROBOT_SIZE)
        };

        SquareObject[] smallJunctions = new SquareObject[] {
                new SquareObject(24, 48, ROBOT_SIZE),
                new SquareObject(24, 96, ROBOT_SIZE),
                new SquareObject(48, 24, ROBOT_SIZE),
                new SquareObject(48, 120, ROBOT_SIZE),
                new SquareObject(96, 24, ROBOT_SIZE),
                new SquareObject(96, 120, ROBOT_SIZE),
                new SquareObject(120, 48, ROBOT_SIZE),
                new SquareObject(120, 96, ROBOT_SIZE),
        };

        SquareObject[] mediumJunctions = new SquareObject[] {
                new SquareObject(48, 48, ROBOT_SIZE),
                new SquareObject(48, 96, ROBOT_SIZE),
                new SquareObject(96, 48, ROBOT_SIZE),
                new SquareObject(96, 96, ROBOT_SIZE)
        };


        SquareObject[] tallJunctions = new SquareObject[] {
                new SquareObject(48, 72, ROBOT_SIZE),
                new SquareObject(72, 96, ROBOT_SIZE),
                new SquareObject(96, 72, ROBOT_SIZE),
                new SquareObject(72, 48, ROBOT_SIZE)
        };*/

        SquareObject[] signals = new SquareObject[] {
                new SquareObject(36, 36, ROBOT_SIZE),
                new SquareObject(108,36, ROBOT_SIZE),
                new SquareObject(36, 108, ROBOT_SIZE),
                new SquareObject(108, 108, ROBOT_SIZE)
        };

/*        List allObjects = Stream.concat(
            Stream.concat(
                Arrays.stream(floorJunctions),
                Arrays.stream(smallJunctions)
            ),
            Stream.concat(
                    Arrays.stream(mediumJunctions),
                    Stream.concat(
                            Arrays.stream(tallJunctions),
                            Arrays.stream(signals)
                    )
            )
        ).toList();*/

/*        for (ListIterator iter = allObjects.listIterator(); iter.hasNext(); ) {
            JComponent currentObject = (JComponent) iter.next();
            this.addObstacle((SquareObject) currentObject);
            this.allObjects.add((Obstacle) currentObject);
        }*/

        for (int i = 0; i < signals.length; i++) {
            this.addObstacle(signals[i]);
            this.allObjects.add(signals[i]);
        }
    }

    public void visualizeCommandLine() {
        for (int n = 0; n < this.adjacencyMatrix.length; n++) {
            StringBuilder lineString = new StringBuilder("[");
            for (int m = 0; m < this.adjacencyMatrix.length; m++) {
                lineString.append(this.adjacencyMatrix[n][m]).append((m == this.adjacencyMatrix.length - 1) ? "]" : ", ");
            }
            System.out.println(lineString);
        }
        System.out.println("");
    }

    public void visualize() {
        GraphPanel objectPanel = new GraphPanel(this.allObjects);
        objectPanel.createAndShowGui();

    }


    private int getIndexFromPosition(double position) {
        return (int) (position / UNIT_LENGTH);
    }
}
