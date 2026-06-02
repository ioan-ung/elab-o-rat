package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile
{
    private static final int NO_TILES = 100;
    public static Tile[] tiles = new Tile[NO_TILES];

    public static void Init()
    {
        int id = -1; // Next is 0  | Floor tiles
        new FloorTile(++id, AssetManager.getSprite("floor"));
        new FloorTile(++id, AssetManager.getSprite("floorWireHorizontal"));
        new FloorTile(++id, AssetManager.getSprite("floorWireVertical"));
        new FloorTile(++id, AssetManager.getSprite("floorWireSW"));
        new FloorTile(++id, AssetManager.getSprite("floorWireSE"));
        new FloorTile(++id, AssetManager.getSprite("floorWireNW"));
        new FloorTile(++id, AssetManager.getSprite("floorWireNE"));

        id = 19; // Next is 20 | Closed/Open door tiles
        new DoorTile(++id,     AssetManager.getSprite("doorR"));
        new DoorTile(++id,     AssetManager.getSprite("doorL"));
        new DoorTile(++id,     AssetManager.getSprite("doorT"));
        new DoorTile(++id,     AssetManager.getSprite("doorB"));
        new DoorTile(++id,     AssetManager.getSprite("doorNoKeyV"));
        new DoorTile(++id,     AssetManager.getSprite("doorNoKeyH"));
        new OpenDoorTile(++id, AssetManager.getSprite("doorROpen"));
        new OpenDoorTile(++id, AssetManager.getSprite("doorLOpen"));
        new OpenDoorTile(++id, AssetManager.getSprite("doorTOpen"));
        new OpenDoorTile(++id, AssetManager.getSprite("doorBOpen"));

        id = 39; // Next is 40 | Wall tiles (0-44)
        for (int i = 0; i <= 44; i++)
            new WallTile(++id, AssetManager.getSprite("wallTile" + i));
        // Next id is 85
    }

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
        g.drawImage(img, x, y, AssetManager.TILE_SIZE, AssetManager.TILE_SIZE, null);
    }

    public boolean isOnXAxis() {return true;}
    public boolean IsSolid() { return false; }
    public boolean IsBoxSolid() { return false; }
    public int GetId() { return id; }
}
