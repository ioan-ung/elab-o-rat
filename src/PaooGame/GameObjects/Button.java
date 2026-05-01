package PaooGame.GameObjects;

import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.Level;
import PaooGame.Levels.LevelManager;
import PaooGame.Tiles.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.AssetManager.*;


public class Button extends GameObject{
    protected Direction direction;
    protected BufferedImage activeImage;
    protected boolean openDoor = true;
    protected int doorX, doorY;

    public Button (int x, int y, Direction direction, int doorX, int doorY) {
        // Set map coordinates
        this.x = x;
        this.y = y;
        this.direction = direction;

        this.doorX = doorX;
        this.doorY = doorY;
        setSprites();
        hitBox = new Rectangle(10,10,12,12);
    }

    @Override
    public void hasCollided() {
        if (openDoor) LevelManager.currentLevel.openDoorAt(doorX,doorY);
        openDoor = false;
        baseImage = activeImage;
    }
    @Override
    public void update() {}
    @Override
    protected void setSprites() {
        switch (direction) {
            case NORTH:
                baseImage = basicButtonWireTop;
                activeImage = AssetManager.getInstance().getSprite("basicButtonPressedWireTop", 5, 7);
                return;
            case EAST:
                baseImage = basicButtonWireRight;
                activeImage = AssetManager.getInstance().getSprite("basicButtonPressedWireRight", 6, 7);
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
