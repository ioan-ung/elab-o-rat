package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.Input.KeyHandler;
import PaooGame.States.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    private Canvas canvas;
    private KeyHandler keyH;

    /// Constructor of Player
    public Player (Canvas canvas, KeyHandler keyH) {
        this.keyH = keyH;
        this.canvas = canvas;
        canvas.addKeyListener(keyH);
        canvas.setFocusable(true);

        setDefaultValues();
        setPlayerSprites();
    }
    /// Method for setting default position and speed
    private void setDefaultValues() {
        x = 96;
        y = 864;
        speed = 5;
        direction = "east";
    }
    /// Method for setting player sprites
    private void setPlayerSprites() {
        north = Assets.playerUp;
        east = Assets.playerRight;
        south = Assets.playerDown;
        west = Assets.playerLeft;
    }
    /// Update player position
    public void update() {
        if (keyH.isUpPressed()) {
            direction = "north";
            y -= speed;
        } else                              // Currently using else-if statement until we add diagonal sprites
        if (keyH.isRightPressed()) {
            direction = "east";
            x += speed;
        } else                              // For diagonal sprites, we might need x-direction and y-direction instead
        if (keyH.isDownPressed()) {
            direction = "south";
            y += speed;
        } else
        if (keyH.isLeftPressed()) {
            direction = "west";
            x -= speed;
        }
    }
    /// Draw player sprite
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "north":
                image = north;
                break;
            case "east":
                image = east;
                break;
            case "south":
                image = south;
                break;
            case "west":
                image = west;
                break;
        }
        g2.drawImage(image,x,y,Assets.TILE_SIZE,Assets.TILE_SIZE, null);
    }
}
