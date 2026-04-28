package PaooGame.Entity;

import java.awt.image.BufferedImage;

public class Entity {
    // Coordinates and speed
    protected int x, y, speed;
    // Direction variables
    protected byte xSign, ySign;
    // Directional sprites
    protected BufferedImage north, northeast, east, southeast, south, southwest, west, northwest;
    protected BufferedImage lastImage;      // Used when no input is given

    // Getters
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
