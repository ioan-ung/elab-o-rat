package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;

// dala solida de tip cutie
public class BoxTile extends Tile
{
    public BoxTile(int id)
    {
        super(AssetManager.box, id);
    }

    @Override
    public boolean IsSolid()
    {
        return true;
    }
}