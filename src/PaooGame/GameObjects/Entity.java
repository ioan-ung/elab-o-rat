package PaooGame.GameObjects;

import java.awt.image.BufferedImage;


public abstract class Entity extends GameObject {

    protected int speed;    // Speed
    // Direction variables | last input sets sign based on direction
    protected byte xSign, ySign;


    public Entity(int x, int y) {
        super(x, y);
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
