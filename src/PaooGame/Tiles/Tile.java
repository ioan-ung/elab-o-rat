package PaooGame.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile
{
    private static final int NO_TILES = 500;
    public static Tile[] tiles = new Tile[NO_TILES];

    public static Tile floorTile;
    public static Tile wallTile;
    public static Tile doorTile;
    public static Tile cheeseTile;

    public static void Init()
    {
        floorTile  = new FloorTile(0);
        wallTile   = new WallTile(1);
        doorTile   = new DoorTile(2);
        cheeseTile = new CheeseTile(3);
    }

    public static final int TILE_WIDTH  = 32;
    public static final int TILE_HEIGHT = 32;

    protected BufferedImage img;
    protected final int id;

    public Tile(BufferedImage image, int id)
    {
        img       = image;
        this.id   = id;
        tiles[id] = this;
    }

    public void Update() {}

    public void Draw(Graphics g, int x, int y)
    {
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean IsSolid() { return false; }
    public int GetId()       { return id;    }
}