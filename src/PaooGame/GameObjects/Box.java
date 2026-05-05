package PaooGame.GameObjects;

import PaooGame.CollisionChecker;
import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.Level;

import java.awt.*;

public class Box extends Entity{
    private int xOg, yOg;                       // Remembers original position of the box
    public Box (int x, int y) {
        // Set map coordinates
        this.x = xOg = x;
        this.y = yOg = y;

        speed = 0;
        hitBox = new Rectangle(6,6,20,20);
        setSprites();
    }


    @Override
    public void hasCollided() {}

    @Override
    public void update() {
        // If inside a tile returns the box to its original position
        if (collision && CollisionChecker.checkTile(this,0,0)) {
            x = xOg;
            y = yOg;
            collision = false;
        }
    }

    @Override
    protected void setSprites() {
        baseImage = AssetManager.box;
    }
}
