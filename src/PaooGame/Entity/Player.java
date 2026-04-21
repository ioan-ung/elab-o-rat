package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.Input.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    private KeyHandler keyH;

    /// Constructor of Player
    public Player (KeyHandler keyH) {
        this.keyH = keyH;

        setDefaultValues();
        setPlayerSprites();
    }
    /// Method for setting default position and speed
    private void setDefaultValues() {
        x = 96*Assets.SCALE;
        y = 864*Assets.SCALE;
        speed = 3*Assets.SCALE;
        xSign = 1; // Looking in the positive x direction
        ySign = 0;
        lastImage = Assets.playerEast;
    }
    /// Method for setting player sprites
    private void setPlayerSprites() {
        north = Assets.playerNorth;
        east = Assets.playerEast;
        south = Assets.playerSouth;
        west = Assets.playerWest;
        northwest = Assets.playerNorthWest;
        northeast = Assets.playerNorthEast;
        southeast = Assets.playerSouthEast;
        southwest = Assets.playerSouthWest;
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
        g2.drawImage(image,x,y,Assets.TILE_SIZE,Assets.TILE_SIZE, null);
    }
}
