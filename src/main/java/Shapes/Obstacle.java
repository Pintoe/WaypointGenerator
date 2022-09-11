package Shapes;

import java.awt.*;

public interface Obstacle {

    public void setPositionX(double newPositionX);

    public void setPositionY(double newPositionY);

    public void setSize(double size);

    public double getPositionX();

    public double getPositionY();

    public double getTotalSize();
}
