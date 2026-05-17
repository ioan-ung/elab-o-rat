package PaooGame.GameObjects.Entities;

import PaooGame.Graphics.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Mouse extends Entity{
    // Directional sprites
    protected BufferedImage north, northeast, east, southeast, south, southwest, west, northwest;

    public Mouse(int x, int y) {
        super(x, y);
        hitBox = new Rectangle(10,10,12,12);
        setSprites();
        baseImage = north;
    }

    @Override // Updates mouse image
    public void update() {
        BufferedImage image;
        // Update base image
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
            else image = baseImage;
        }
        baseImage = image;
    }

    @Override // Method for setting mouse sprites
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
}
