package Shapes;

import javax.swing.*;
import java.awt.*;

public class SquareObject extends JComponent implements Obstacle {
    private double positionX;
    private double positionY;

    private double size;

    public SquareObject(double positionX, double positionY, double size) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = size;
    }

    @Override
    public void paintComponent(Graphics graphicsObject) {
        super.paintComponent(graphicsObject);
        Rectangle newRectangle = new Rectangle((int) this.positionX, (int) this.positionY, (int) this.size, (int) this.size);
        Graphics2D newGraphicsObject = (Graphics2D) graphicsObject.create();
        newGraphicsObject.draw(newRectangle);
    }

    @Override
    public void setPositionX(double newPositionX) {
        this.positionX = newPositionX;
    }

    @Override
    public void setPositionY(double newPositionY) {
        this.positionY = newPositionY;
    }

    @Override
    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public double getPositionX() {
        return this.positionX;
    }

    @Override
    public double getPositionY() {
        return this.positionY;
    }

    @Override
    public double getTotalSize() {
        return this.size;
    }

}
