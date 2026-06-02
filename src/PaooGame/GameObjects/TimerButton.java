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
        save = false;
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
        else if (collision) {   // Closes the door when there's no collision if it's open
            LevelManager.currentLevel.closeDoorAt(doorX, doorY);
            baseImage = inactiveImage;
            collision = false;
        }
    }

    @Override
    protected void setSprites() {
        // Set sprites based on direction
        switch (direction) {
            case NORTH:
                inactiveImage = AssetManager.getSprite("timedButtonWireTop");
                activeImage = AssetManager.getSprite("timedButtonPressedWireTop", 7, 5);
                break;
            case EAST:
                inactiveImage = AssetManager.getSprite("timedButtonWireRight");
                activeImage = AssetManager.getSprite("timedButtonPressedWireRight", 4, 5);
                break;
            case SOUTH:
                inactiveImage = AssetManager.getSprite("timedButtonWireBottom");
                activeImage = AssetManager.getSprite("timedButtonPressedWireBottom", 7, 4);
                break;
            case WEST:
                inactiveImage = AssetManager.getSprite("timedButtonWireLeft");
                activeImage = AssetManager.getSprite("timedButtonPressedWireLeft", 5, 5);
        }
        baseImage = inactiveImage;
    }
}
