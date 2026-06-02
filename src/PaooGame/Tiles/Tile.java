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

        id = 39; // Next is 40 | Wall tiles
        new WallTile(++id, AssetManager.getSprite("wallTile0"));
        new WallTile(++id, AssetManager.getSprite("wallTile1"));
        new WallTile(++id, AssetManager.getSprite("wallTile2"));
        new WallTile(++id, AssetManager.getSprite("wallTile3"));
        new WallTile(++id, AssetManager.getSprite("wallTile4"));
        new WallTile(++id, AssetManager.getSprite("wallTile5"));
        new WallTile(++id, AssetManager.getSprite("wallTile6"));
        new WallTile(++id, AssetManager.getSprite("wallTile7"));
        new WallTile(++id, AssetManager.getSprite("wallTile8"));
        new WallTile(++id, AssetManager.getSprite("wallTile9"));
        new WallTile(++id, AssetManager.getSprite("wallTile10"));
        new WallTile(++id, AssetManager.getSprite("wallTile11"));
        new WallTile(++id, AssetManager.getSprite("wallTile12"));
        new WallTile(++id, AssetManager.getSprite("wallTile13"));
        new WallTile(++id, AssetManager.getSprite("wallTile14"));
        new WallTile(++id, AssetManager.getSprite("wallTile15"));
        new WallTile(++id, AssetManager.getSprite("wallTile16"));
        new WallTile(++id, AssetManager.getSprite("wallTile17"));
        new WallTile(++id, AssetManager.getSprite("wallTile18"));
        new WallTile(++id, AssetManager.getSprite("wallTile19"));
        new WallTile(++id, AssetManager.getSprite("wallTile20"));
        new WallTile(++id, AssetManager.getSprite("wallTile21"));
        new WallTile(++id, AssetManager.getSprite("wallTile22"));
        new WallTile(++id, AssetManager.getSprite("wallTile23"));
        new WallTile(++id, AssetManager.getSprite("wallTile24"));
        new WallTile(++id, AssetManager.getSprite("wallTile25"));
        new WallTile(++id, AssetManager.getSprite("wallTile26"));
        new WallTile(++id, AssetManager.getSprite("wallTile27"));
        new WallTile(++id, AssetManager.getSprite("wallTile28"));
        new WallTile(++id, AssetManager.getSprite("wallTile29"));
        new WallTile(++id, AssetManager.getSprite("wallTile30"));
        new WallTile(++id, AssetManager.getSprite("wallTile31"));
        new WallTile(++id, AssetManager.getSprite("wallTile32"));
        new WallTile(++id, AssetManager.getSprite("wallTile33"));
        new WallTile(++id, AssetManager.getSprite("wallTile34"));
        new WallTile(++id, AssetManager.getSprite("wallTile35"));
        new WallTile(++id, AssetManager.getSprite("wallTile36"));
        new WallTile(++id, AssetManager.getSprite("wallTile37"));
        new WallTile(++id, AssetManager.getSprite("wallTile38"));
        new WallTile(++id, AssetManager.getSprite("wallTile39"));
        new WallTile(++id, AssetManager.getSprite("wallTile40"));
        new WallTile(++id, AssetManager.getSprite("wallTile41"));
        new WallTile(++id, AssetManager.getSprite("wallTile42"));
        new WallTile(++id, AssetManager.getSprite("wallTile43"));
        new WallTile(++id, AssetManager.getSprite("wallTile44"));
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
