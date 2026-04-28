package PaooGame.Entity;

import PaooGame.Graphics.AssetManager;
import PaooGame.Input.KeyManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mouse extends Entity{

    private KeyManager keyH;

    /// Constructor of Player
    public Mouse(KeyManager keyH) {
        this.keyH = keyH;

        setDefaultValues();
        setPlayerSprites();
    }
    /// Method for setting default position and speed
    private void setDefaultValues() {
        x = 96* AssetManager.SCALE;
        y = 32* AssetManager.SCALE;
        speed = 3* AssetManager.SCALE;
        xSign = 1; // Looking in the positive x direction
        ySign = 0;
        lastImage = AssetManager.playerEast;
    }
    /// Method for setting player sprites
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
    /// Update player position
    public void update() {
        if (keyH.isUpPressed()) {
            ySign = -1;
            y -= speed;
        }
        if (keyH.isDownPressed()) {
            ySign = 1;
            y += speed;
        }
        if (!(keyH.isUpPressed() || keyH.isDownPressed())) ySign = 0;
        if (keyH.isRightPressed()) {
            xSign = 1;
            x += speed;
        }
        if (keyH.isLeftPressed()) {
            xSign = -1;
            x -= speed;
        }
        if (!(keyH.isRightPressed() || keyH.isLeftPressed())) xSign = 0;
    }
    /// Draw player sprite
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
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
        g2.drawImage(image,x,y, AssetManager.TILE_SIZE, AssetManager.TILE_SIZE, null);
    }
}
