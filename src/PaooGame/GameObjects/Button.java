package PaooGame.GameObjects;

import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.LevelManager;
import PaooGame.Tiles.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.AssetManager.*;


public class Button extends GameObject{
    protected Direction direction;
    protected BufferedImage activeImage;    // Used when button is active
    protected int doorX, doorY;             // Map coordinates of linked door

    public Button (int x, int y, Direction direction, int doorX, int doorY) {
        // Set coordinates
        this.x = x;
        this.y = y;
        this.direction = direction;
        // Set door coordinates
        this.doorX = doorX;
        this.doorY = doorY;
        // Set hitbox
        hitBox = new Rectangle(10,10,10,10);

        collision = true;      // False when button is active
        setSprites();
    }

    @Override
    public void hasCollided() {
        // Open the door at collision if it's closed
        if (collision) {     // Opens only if it's closed
            LevelManager.currentLevel.openDoorAt(doorX, doorY);
            baseImage = activeImage;    // Change the image to draw
        }
        collision = false;
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
