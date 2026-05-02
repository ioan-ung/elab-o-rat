package PaooGame.GameObjects;

import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.LevelManager;
import PaooGame.Tiles.Direction;

import java.awt.image.BufferedImage;

import static PaooGame.Graphics.AssetManager.*;
import static PaooGame.Graphics.AssetManager.timedButtonWireLeft;

public class BoxButton extends Button{
    protected BufferedImage inactiveImage;  // Used when button is NOT active
    protected boolean hasBox = false;

    public BoxButton(int x, int y, Direction direction, int doorX, int doorY) {
        super(x, y, direction, doorX, doorY);
    }


    @Override
    public void hasCollided() {
        hasBox = true;
        super.hasCollided();
    }

    @Override
    public void update() {
        if (hasBox) hasBox = false;
        else {
            if (!collision) {   // Closes the door if it's open when there's no collision
                LevelManager.currentLevel.closeDoorAt(doorX, doorY);
                baseImage = inactiveImage;
            }
            collision = true;
        }
    }

    @Override
    protected void setSprites() {
        // Set sprites based on direction
        switch (direction) {
            case NORTH:
                inactiveImage = boxButtonWireTop;
                activeImage = AssetManager.getInstance().getSprite("timedButtonPressedWireTop", 7, 5);
                break;
            case EAST:
                inactiveImage = boxButtonWireRight;
                activeImage = AssetManager.getInstance().getSprite("timedButtonPressedWireRight", 4, 5);
                break;
            case SOUTH:
                inactiveImage = boxButtonWireBottom;
                activeImage = AssetManager.getInstance().getSprite("timedButtonPressedWireBottom", 7, 4);
                break;
            case WEST:
                inactiveImage = boxButtonWireLeft;
                activeImage = AssetManager.getInstance().getSprite("timedButtonPressedWireLeft", 5, 5);
        }
        baseImage = inactiveImage;
    }
}
