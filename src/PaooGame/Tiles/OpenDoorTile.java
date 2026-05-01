package PaooGame.Tiles;

import java.awt.image.BufferedImage;

public class OpenDoorTile extends Tile
{
    public OpenDoorTile(int id, BufferedImage img)
    {
        super(img, id);
    }

    @Override
    public boolean IsSolid()
    {
        return false; // The player can walk through an open door
    }
}