package PaooGame.GameObjects.Entities;

import PaooGame.CollisionChecker;
import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.Level;

import java.awt.*;

public class Cat extends Entity {

    public Cat(int x, int y) {
        super(x, y);
        speed = 2; // mai lent decat playerul (4px/frame)
        hitBox = new Rectangle(10, 10, 12, 12);
        setSprites();
    }

    @Override
    public void hasCollided() {

    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2) {
        if (baseImage != null) { super.draw(g2); return; }
        g2.setColor(new Color(255, 140, 0));
        g2.fillRect(x * AssetManager.SCALE, y * AssetManager.SCALE, AssetManager.TILE_SIZE, AssetManager.TILE_SIZE);
    }


    @Override
    protected void setSprites() {
        baseImage = AssetManager.cat;
    }
}
