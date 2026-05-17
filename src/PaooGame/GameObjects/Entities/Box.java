package PaooGame.GameObjects.Entities;

import PaooGame.Algorithms.CollisionChecker;
import PaooGame.Game;
import PaooGame.Graphics.AssetManager;

import java.awt.*;

public class Box extends Entity{
    private final int xOg, yOg;   // Remembers original position of the box
    public Box (int x, int y) {
        super(x,y);
        xOg = x;
        yOg = y;

        speed = 0;
        hitBox = new Rectangle(6,6,20,20);
        setSprites();
    }



    @Override
    public void update() {
        // If inside a tile returns the box to its original position
        if (collision && CollisionChecker.checkTile(this,0,0)) {
            x = xOg;
            y = yOg;
            collision = false;
            Game.playSoundEfx(3);
        }
    }

    @Override
    protected void setSprites() {
        baseImage = AssetManager.box;
    }
}
