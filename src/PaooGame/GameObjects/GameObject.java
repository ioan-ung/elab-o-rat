package PaooGame.GameObjects;

import PaooGame.Input.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.AssetManager.SCALE;
import static PaooGame.Graphics.AssetManager.TILE_SIZE;

public abstract class GameObject {
    protected int x, y;
    protected Rectangle hitBox;
    protected BufferedImage baseImage;
    protected boolean collision;

    public GameObject (int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Abstract methods
    public abstract void hasCollided();
    public abstract void update();
    protected abstract void setSprites();

    // Draws object
    public void draw(Graphics2D g2) {
        g2.drawImage(baseImage, x * SCALE, y * SCALE, TILE_SIZE, TILE_SIZE, null);
        // DEBUG: Hitbox
        if (KeyHandler.debugOn) drawHitbox(g2);
    }
    // Draws hitbox
    private void drawHitbox(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);
        g2d.drawRect((x + hitBox.x)*SCALE,(y + hitBox.y)*SCALE, hitBox.width*SCALE, hitBox.height*SCALE);
    }
    // Getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Rectangle getRect() {
        return hitBox;
    }

    // Simply moves the object
    public void move (int x, int y) {
        this.x = x;
        this.y = y;
        collision = true;
    }
}
