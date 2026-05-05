package PaooGame.GameObjects;

import PaooGame.CollisionChecker;
import PaooGame.Graphics.AssetManager;
import PaooGame.Input.KeyHandler;

import java.awt.*;

public class Player extends Mouse{
    protected KeyHandler keyH;    // Handles keyboard input

    // Constructor of Player
    public Player(KeyHandler keyH) {
        super(10,10);
        this.keyH = keyH;
    }

    // Method for setting default position and speed
    public void setDefaultValues(int x, int y) {
        // Position and speed
        this.x = x-x%4;   /// Speed affects how close the hitbox can get to the wall TODO: fix bug?
        this.y = y-y%4;   /// Setting the speed to 4 (divides 32) and shifting the default positions is a temporary fix
        speed = 4;      /// after which the effective hitbox becomes is 16x16
        // Looking in the positive x direction (east)
        xSign = ySign = 0;
    }

    @Override // Method for setting player sprites
    protected void setSprites() {
        north = AssetManager.mouseNorth;
        east = AssetManager.mouseEast;
        south = AssetManager.mouseSouth;
        west = AssetManager.mouseWest;
        northwest = AssetManager.mouseNorthWest;
        northeast = AssetManager.mouseNorthEast;
        southeast = AssetManager.mouseSouthEast;
        southwest = AssetManager.mouseSouthWest;
    }

    @Override // Updates player position and faced direction
    public void update() {
        super.update();
        // First, set direction of movement
        xSign = ySign = 0;
        if (keyH.isUpPressed())    ySign -= 1;
        if (keyH.isDownPressed())  ySign += 1;
        if (keyH.isLeftPressed())  xSign -= 1;
        if (keyH.isRightPressed()) xSign += 1;
        // Second, change position based on speed and collision
        hasCollidedX = hasCollidedY = false;
        if (!(ySign == 0 || CollisionChecker.checkTile(this,0, ySign))) if (!hasCollidedY) y += speed * ySign;
        if (!(xSign == 0 || CollisionChecker.checkTile(this, xSign, 0))) if (!hasCollidedX) x += speed * xSign;
    }
}
