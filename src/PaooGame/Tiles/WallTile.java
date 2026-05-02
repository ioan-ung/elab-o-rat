package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;

// dala solida de tip perete
public class WallTile extends Tile
{
    public WallTile(int id)
    {
        super(AssetManager.wall, id);
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