package PaooGame.GameObjects;

import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.LevelManager;
import PaooGame.Algorithms.Direction;

import java.awt.image.BufferedImage;

import static PaooGame.Graphics.AssetManager.*;

public class TimerButton extends Button{
    private final int setCount;             // Used to set countdown in frames
    private int countdown = 0;              // Per frame countdown
    protected BufferedImage inactiveImage;  // Used when button is NOT active

    public TimerButton(int x, int y, Direction direction, int doorX, int doorY, int frames) {
        // Call simple button constructor
        super(x, y, direction, doorX, doorY);
        setCount = frames;
    }

    @Override
    public void hasCollided() {
        countdown = setCount;   // Reset countdown and open door
        super.hasCollided();
    }

    @Override
    public void update() {  // Decrements the countdown
        if (countdown > 0) --countdown;
        else {
            if (!collision) {   // Closes the door when there's no collision if it's open
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
                inactiveImage = timedButtonWireTop;
                activeImage = AssetManager.getInstance().getSprite("timedButtonPressedWireTop", 7, 5);
                break;
            case EAST:
                inactiveImage = timedButtonWireRight;
                activeImage = AssetManager.getInstance().getSprite("timedButtonPressedWireRight", 4, 5);
                break;
            case SOUTH:
                inactiveImage = timedButtonWireBottom;
                activeImage = AssetManager.getInstance().getSprite("timedButtonPressedWireBottom", 7, 4);
                break;
            case WEST:
                inactiveImage = timedButtonWireLeft;
                activeImage = AssetManager.getInstance().getSprite("timedButtonPressedWireLeft", 5, 5);
        }
        baseImage = inactiveImage;
    }
}
