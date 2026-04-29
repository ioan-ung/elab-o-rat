package PaooGame.Entity;

import PaooGame.CollisionChecker;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public abstract class Entity {
    // Coordinates and speed
    protected int xPos, yPos;
    protected int speed;
    // Direction variables | last input sets sign based on direction
    protected byte xSign, ySign;
    // Directional sprites
    protected BufferedImage north, northeast, east, southeast, south, southwest, west, northwest;
    protected BufferedImage lastImage;      // Used when no input is given

    // Entity hitbox
    protected CollisionChecker collisionChecker;
    protected Rectangle solidArea;
    protected boolean hasCollidedY = false;  // Stops movement in Y
    protected boolean hasCollidedX = false;  // Stops movement in X
    // Setters
    public void setXCollision(boolean value) {
        hasCollidedX = value;
    }
    public void setYCollision(boolean value) {
        hasCollidedY = value;
    }
    // Getters
    public int getXPos() {
        return xPos;
    }
    public int getYPos() {
        return yPos;
    }
    public int getSpeed() {
        return speed;
    }
    public int getXSign(){
        return xSign;
    }
    public int getYSign(){
        return ySign;
    }
    public Rectangle getRect() {
        return solidArea;
    }
}
