package Shapes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleObject extends JComponent implements Obstacle {
    private double positionX;
    private double positionY;

    private double size;

    public CircleObject(double positionX, double positionY, double size) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = size;
    }

    @Override
    public void paintComponent(Graphics graphicsObject) {
        super.paintComponent(graphicsObject);
        Shape newCircle = new Ellipse2D.Double((int) this.positionX, (int) this.positionY, (int) this.size, (int) this.size);
        Graphics2D newGraphicsObject = (Graphics2D) graphicsObject.create();
        newGraphicsObject.draw(newCircle);
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
