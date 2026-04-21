package PaooGame.Entity;

import java.awt.image.BufferedImage;

public class Entity {
    // Coordinates and speed
    protected int x;
    protected int y;
    protected int speed;
    // Directional sprites
    protected BufferedImage north, northeast, east, southeast, south, southwest, west, northwest;
    protected BufferedImage lastImage;
    protected byte xSign, ySign;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSpeed() {
        return speed;
    }
}
