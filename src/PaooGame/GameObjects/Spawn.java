package PaooGame.GameObjects;

import java.awt.*;

public class Spawn extends GameObject{

    public Spawn (int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public void hasCollided() {}
    @Override
    public void update() {}
    @Override
    public void draw(Graphics2D g) {}
    @Override
    protected void setSprites() {}
}
