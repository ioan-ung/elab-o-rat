package PaooGame.GameObjects.Entities;

import PaooGame.GameObjects.GameObject;


public abstract class Entity extends GameObject {

    protected int speed;    // Speed
    // Direction variables | last input sets sign based on direction
    protected int xSign, ySign;


    public Entity(int x, int y) {
        super(x, y);
    }

    // Getters
    public int getSpeed() {
        return speed;
    }
    public int getXSign() {
        return xSign;
    }
    public int getYSign() {
        return ySign;
    }

    @Override
    public void act() {}
    @Override
    public void hasCollided() {}
}
