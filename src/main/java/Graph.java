import Shapes.CircleObject;
import Shapes.Obstacle;
import Shapes.ShapeType;
import Shapes.SquareObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

import javax.swing.*;

public class Graph {
    public static final int FIELD_LENGTH = 144; // inches
    private static final double UNIT_LENGTH = 1.4D; // inches
    private static final int TOTAL_AMOUNT_OF_UNITS = (int) (FIELD_LENGTH / UNIT_LENGTH);

    private static final int ROBOT_SIZE = 18;

    private static final int FLOOR_JUNCTION_OFFSET = 3;
    private static final int JUNCTION_OFFSET = 1;
    private static final int SIGNAL_OFFSET = 1;
    private final boolean[][] adjacencyMatrix;
    
    
    ArrayList<Obstacle> allObjects = new ArrayList<>();
    public Graph() {
        this.adjacencyMatrix = new boolean[TOTAL_AMOUNT_OF_UNITS][TOTAL_AMOUNT_OF_UNITS];

        for (int i = 0; i < TOTAL_AMOUNT_OF_UNITS; i++) {
            for (int j = 0; j < TOTAL_AMOUNT_OF_UNITS; j++) {
                this.adjacencyMatrix[i][j] = i != j;
            }
        }
    }

    public void addObstacle(Obstacle newObject) {
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

        int maxIndexDistance = (int) Math.pow((double) ((newObject.getTotalSize() / 2) / Graph.UNIT_LENGTH), 2);

        System.out.println(maxIndexDistance);
        System.out.println(newObject.getTotalSize());
        for (int startYIndex = yBounds[0]; startYIndex < yBounds[1]; startYIndex++) {
            for (int startXIndex = xBounds[0]; startXIndex < xBounds[1]; startXIndex++) {
                switch (newObject.getShape()) {
                    case SQUARE:
                        this.adjacencyMatrix[startYIndex][startXIndex] = false;
                        this.adjacencyMatrix[startXIndex][startYIndex] = false;
                    case CIRCLE: {
                        if (maxIndexDistance > Math.pow(startXIndex - middleXIndex, 2) + Math.pow(startYIndex - middleYIndex, 2)) {
                            this.adjacencyMatrix[startYIndex][startXIndex] = false;
                            this.adjacencyMatrix[startXIndex][startYIndex] = false;
                        }
                        System.out.println(Math.pow(startXIndex - middleXIndex, 2) + Math.pow(startYIndex - middleYIndex, 2));
                    }
                }
            }
        }

    }

    public void addPowerPlayFieldObstacles() {

        CircleObject[] floorJunctions = new CircleObject[] {
                new CircleObject(24, 24, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET),
                new CircleObject(72, 24, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET),
                new CircleObject(120, 24, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET),
                new CircleObject(24, 72, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET),
                new CircleObject(72, 72, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET),
                new CircleObject(120, 72, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET),
                new CircleObject(24, 120, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET),
                new CircleObject(72, 120, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET),
                new CircleObject(120, 120, ROBOT_SIZE + Graph.FLOOR_JUNCTION_OFFSET)
        };

        CircleObject[] smallJunctions = new CircleObject[] {
                new CircleObject(24, 48, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(24, 96, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(48, 24, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(48, 120, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(96, 24, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(96, 120, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(120, 48, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(120, 96, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
        };

        CircleObject[] mediumJunctions = new CircleObject[] {
                new CircleObject(48, 48, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(48, 96, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(96, 48, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(96, 96, ROBOT_SIZE + Graph.JUNCTION_OFFSET)
        };


        CircleObject[] tallJunctions = new CircleObject[] {
                new CircleObject(48, 72, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(72, 96, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(96, 72, ROBOT_SIZE + Graph.JUNCTION_OFFSET),
                new CircleObject(72, 48, ROBOT_SIZE + Graph.JUNCTION_OFFSET)
        };

        CircleObject[] signals = new CircleObject[] {
                new CircleObject(36, 36, ROBOT_SIZE + Graph.SIGNAL_OFFSET),
                new CircleObject(108,36, ROBOT_SIZE + Graph.SIGNAL_OFFSET),
                new CircleObject(36, 108, ROBOT_SIZE + Graph.SIGNAL_OFFSET),
                new CircleObject(108, 108, ROBOT_SIZE + Graph.SIGNAL_OFFSET)
        };

        List allObjects = Stream.concat(
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
        ).toList();

        for (ListIterator iter = allObjects.listIterator(); iter.hasNext(); ) {
            Obstacle currentObject = (Obstacle) iter.next();
            this.addObstacle(currentObject);
            this.allObjects.add(currentObject);
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
