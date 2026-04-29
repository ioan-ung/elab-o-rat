package PaooGame.Graphics;

import java.awt.image.BufferedImage;

// retine sprite sheet-ul si returneaza dale individuale prin crop()
public class SpriteSheet
{
    private final BufferedImage     spriteSheet;              /*!< Referinta catre obiectul BufferedImage ce contine sprite sheet-ul.*/
    private static final int        tileWidth   = AssetManager.TILE_ACTUAL_SIZE;   /*!< Latimea unei dale din sprite sheet.*/
    private static final int        tileHeight  = AssetManager.TILE_ACTUAL_SIZE;   /*!< Inaltime unei dale din sprite sheet.*/

    public SpriteSheet(BufferedImage buffImg)
    {
        spriteSheet = buffImg;      // Retine referinta catre BufferedImage object.
    }

    // returneaza imaginea dalei de la pozitia (x, y) din grid
    public BufferedImage crop(int x, int y)
    {
        return spriteSheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
    }
}
