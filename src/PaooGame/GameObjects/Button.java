package PaooGame.GameObjects;

import PaooGame.Graphics.AssetManager;
import PaooGame.Tiles.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;


public class Button extends GameObject{
    protected Direction direction;
    public Button (int tileX, int tileY, Direction direction) {
        // Set map coordinates
        x = tileX * TILE_ACTUAL_SIZE;
        y = tileY * TILE_ACTUAL_SIZE;
        this.direction = direction;
        setSprites();
        hitBox = new Rectangle(10,10,12,12);
    }
    @Override
    public void hasCollided() {
        baseImage = AssetManager.basicButtonWireTop;
    }
    @Override
    public void update() {
        baseImage = AssetManager.basicButtonWireBottom;
    }
    @Override
    protected void setSprites() {
        baseImage = AssetManager.basicButtonWireBottom;
    }
}
