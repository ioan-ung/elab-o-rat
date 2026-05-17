package PaooGame.GameObjects;

import PaooGame.Data.Database;
import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.LevelManager;
import PaooGame.Algorithms.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.AssetManager.*;


public class Button extends GameObject{
    protected final Direction direction;
    protected BufferedImage activeImage;    // Used when button is active
    protected final int doorX, doorY;       // Map coordinates of linked door
    protected boolean save = true;

    public Button (int x, int y, Direction direction, int doorX, int doorY) {
        // Set coordinates and direction
        super(x,y);
        this.direction = direction;
        // Set door coordinates
        this.doorX = doorX;
        this.doorY = doorY;
        // Set hitbox
        hitBox = new Rectangle(10,10,10,10);

        collision = false;      // Turns true when button is activated
        setSprites();
    }

    @Override
    public void hasCollided() {
        if (collision) return;
        act();
        if (save) Database.saveObjChanges(x,y); // Save interraction to DB
    }


    @Override
    public void act() {         // Opens door at (doorX, doorY)
        collision = true;
        LevelManager.currentLevel.openDoorAt(doorX, doorY);
        baseImage = activeImage;    // Change the button's image for draw
    }

    @Override
    public void update() {}     // Simple buttons don't need to self update

    @Override
    protected void setSprites() {
        // Set sprites based on direction
        switch (direction) {
            case NORTH:
                baseImage = basicButtonWireTop;
                activeImage = AssetManager.getInstance().getSprite("basicButtonPressedWireTop", 5, 7);
                return;
            case EAST:
                baseImage = basicButtonWireRight;
                activeImage = AssetManager.getInstance().getSprite("basicButtonPressedWireRight", 5, 8);
                return;
            case SOUTH:
                baseImage = basicButtonWireBottom;
                activeImage = AssetManager.getInstance().getSprite("basicButtonPressedWireBottom", 5, 6);
                return;
            case WEST:
                baseImage = basicButtonWireLeft;
                activeImage = AssetManager.getInstance().getSprite("basicButtonPressedWireLeft", 6, 8);
        }
    }
}
