package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;

import java.awt.image.BufferedImage;

// dala solida de tip perete
public class WallTile extends Tile
{
    public WallTile(int id, BufferedImage img)
    {
        super(img, id);
    }

    @Override
    public boolean IsSolid()
    {
        return true;
    }
    public boolean IsBoxSolid()
    {
        return true; // Boxes cannot go through open doors
    }
}