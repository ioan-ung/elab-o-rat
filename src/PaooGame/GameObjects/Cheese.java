package PaooGame.GameObjects;

import PaooGame.Data.Database;
import PaooGame.Graphics.AssetManager;
import java.awt.*;

public class Cheese extends GameObject{
    private static int cheeseLeft = 0;   // Cheese left to collect

    public Cheese (int x, int y) {
        super(x,y);     // Set coordinates
        ++cheeseLeft;   // Increments no. cheese
        // Set hitbox
        hitBox = new Rectangle(10,10,10,10);
        setSprites();
    }

    @Override // Decrements cheeseLeft and sets collision
    public void hasCollided() {
        if (collision) return;
        act();
        Database.saveObjChanges(x,y); // Save interraction to DB
    }

    @Override
    public void act() {
        // Decrement the cheese left
        collision = true;
        --cheeseLeft;
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g) {
        if (collision) return;  // Return if it's already been collected
        super.draw(g);
    }

    @Override
    protected void setSprites() {
        baseImage = AssetManager.cheese;
    }

    public static int getCheeseLeft() {
        return cheeseLeft;
    }
    public static void resetCheese() {
        cheeseLeft = 0;
    }
}
