package PaooGame.Entity;

import PaooGame.CollisionChecker;
import PaooGame.Graphics.AssetManager;
import PaooGame.Input.KeyHandler;
import PaooGame.Levels.Level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mouse extends Entity{

    private KeyHandler keyH;    // Handles keyboard input

    // Constructor of Player
    public Mouse(KeyHandler keyH, Level level) {
        this.keyH = keyH;

        solidArea = new Rectangle(10,10,12,12);
        collisionChecker = new CollisionChecker(level);
        setDefaultValues();
        setPlayerSprites();
    }
    // Method for setting default position and speed
    private void setDefaultValues() {
        // Position and speed
        xPos = 96;
        yPos = 32;
        speed = 3;
        // Looking in the positive x direction (east)
        xSign = 1;
        ySign = 0;
        lastImage = AssetManager.playerEast;
    }
    // Method for setting player sprites
    private void setPlayerSprites() {
        north = AssetManager.playerNorth;
        east = AssetManager.playerEast;
        south = AssetManager.playerSouth;
        west = AssetManager.playerWest;
        northwest = AssetManager.playerNorthWest;
        northeast = AssetManager.playerNorthEast;
        southeast = AssetManager.playerSouthEast;
        southwest = AssetManager.playerSouthWest;
    }
    // Updates player position and faced direction
    public void update() {
        // First, set direction of movement
        xSign = 0;
        ySign = 0;
        if (keyH.isUpPressed())    ySign -= 1;
        if (keyH.isDownPressed())  ySign += 1;
        if (keyH.isLeftPressed())  xSign -= 1;
        if (keyH.isRightPressed()) xSign += 1;
        // Second, change position based on speed and collision
        hasCollidedX = hasCollidedY = false;
        if (!(ySign == 0 || collisionChecker.checkTile(this,0, ySign))) if (!hasCollidedY) yPos += speed * ySign;
        if (!(xSign == 0 || collisionChecker.checkTile(this, xSign, 0))) if (!hasCollidedX) xPos += speed * xSign;
    }
    // Draws player sprite based on direction
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        // Chooses which sprite to draw
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
            else image = lastImage;
        }
        lastImage = image;
        g2.drawImage(image, xPos * AssetManager.SCALE, yPos * AssetManager.SCALE, AssetManager.TILE_SIZE, AssetManager.TILE_SIZE, null);
    }
}
