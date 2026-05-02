package PaooGame.GameObjects;

import java.awt.image.BufferedImage;


public abstract class Entity extends GameObject {

    protected int speed;    // Speed
    // Direction variables | last input sets sign based on direction
    protected byte xSign, ySign;
    // Directional sprites
    protected BufferedImage north, northeast, east, southeast, south, southwest, west, northwest;

    protected boolean hasCollidedY = false;  // Stops movement in Y
    protected boolean hasCollidedX = false;  // Stops movement in X


    @Override // Updates player position and faced direction
    public void update() {
        BufferedImage image = null;
        // Update base image
        if (ySign==-1) {
            if (xSign==-1) image = northwest;
            else if (xSign==1) image = northeast;
            else image = north;
        } else if (ySign==1) {
            if (xSign==-1) image = southwest;
            else if (xSign==1) image = southeast;
            else image = south;
        } else {
            if (xSign==-1) image = west;
            else if (xSign==1) image = east;
            else image = baseImage;
        }
        baseImage = image;
    }

    // Getters
    public int getSpeed() {
        return speed;
    }

    public byte getXSign() {
        return xSign;
    }

    public byte getYSign() {
        return ySign;
    }
}
