package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;

// dala de tip branza — nesolida, devine inactiva dupa colectare
public class CheeseTile extends Tile
{
    // true = deja colectata
    private boolean collected;

    public CheeseTile(int id)
    {
        super(AssetManager.cheese, id);
        collected = false;
    }

    @Override
    public boolean IsSolid()
    {
        return false;
    }

    public void collect()
    {
        collected = true;
    }

    public boolean isCollected()
    {
        return collected;
    }
}