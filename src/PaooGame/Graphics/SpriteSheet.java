package PaooGame.Graphics;

import java.awt.image.BufferedImage;

// retine sprite sheet-ul si returneaza dale individuale prin crop()
public class SpriteSheet
{
    private final BufferedImage  spriteSheet;
    private static final int     tileWidth  = AssetManager.TILE_ACTUAL_SIZE;
    private static final int     tileHeight = AssetManager.TILE_ACTUAL_SIZE;

    public SpriteSheet(BufferedImage buffImg)
    {
        spriteSheet = buffImg;
    }

    // returneaza dala de la pozitia (x, y) din grid
    public BufferedImage crop(int x, int y)
    {
        return spriteSheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
    }
}
