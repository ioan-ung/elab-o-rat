package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile
{
    private static final int NO_TILES = 500;
    public static Tile[] tiles = new Tile[NO_TILES];

    public static Tile wallTile;
    public static Tile doorR;
    public static Tile doorL;
    public static Tile doorT;
    public static Tile doorB;
    public static Tile doorROpen;
    public static Tile doorLOpen;
    public static Tile doorTOpen;
    public static Tile doorBOpen;
    public static Tile doorNoKeyV;
    public static Tile doorNoKeyH;


    public static Tile cheese;
    public static Tile box;
    public static Tile mouseT;
    public static Tile mouseB;
    public static Tile mouseL;
    public static Tile mouseR;


    public static Tile floor;
    public static Tile floorWireHorizontal;
    public static Tile floorWireVertical;
    public static Tile floorWireSW;
    public static Tile floorWireSE;
    public static Tile floorWireNW;
    public static Tile floorWireNE;

    public static void Init()
    {
        int id = -1;
        floor  = new FloorTile(++id, AssetManager.getInstance().getSprite("floor", 1, 7));
        floorWireHorizontal = new FloorTile(++id, AssetManager.getInstance().getSprite("floorWireHorizontal", 1, 6));
        floorWireVertical = new FloorTile(++id, AssetManager.getInstance().getSprite("floorWireVertical", 0, 7));
        floorWireSW = new FloorTile(++id, AssetManager.getInstance().getSprite("floorWireSW", 2, 6));
        floorWireSE = new FloorTile(++id, AssetManager.getInstance().getSprite("floorWireSE", 0, 6));
        floorWireNW = new FloorTile(++id, AssetManager.getInstance().getSprite("floorWireNW", 3, 8));
        floorWireNE = new FloorTile(++id, AssetManager.getInstance().getSprite("floorWireNE", 0, 8));
        id = 9; // Next is 10
        wallTile   = new WallTile(++id);

        id = 19; // Next is 20
        doorR = new DoorTile(++id, AssetManager.getInstance().getSprite("doorR", 3, 6));
        doorL = new DoorTile(++id, AssetManager.getInstance().getSprite("doorL", 4, 6));
        doorT   = new DoorTile(++id, AssetManager.getInstance().getSprite("doorT", 4, 8));
        doorB   = new DoorTile(++id, AssetManager.getInstance().getSprite("doorB", 4, 7));
        doorNoKeyV  = new DoorTile(++id, AssetManager.getInstance().getSprite("doorNoKeyV", 8, 4));
        doorNoKeyH  = new DoorTile(++id, AssetManager.getInstance().getSprite("doorNoKeyH", 8, 5));

        doorROpen = new OpenDoorTile(++id, AssetManager.getInstance().getSprite("doorROpen", 6, 6));
        doorLOpen = new OpenDoorTile(++id, AssetManager.getInstance().getSprite("doorLOpen", 7, 6));
        doorTOpen   = new OpenDoorTile(++id, AssetManager.getInstance().getSprite("doorTOpen", 7, 8));
        doorBOpen   = new OpenDoorTile(++id, AssetManager.getInstance().getSprite("doorBOpen", 7, 7));


        cheese = new CheeseTile(++id);
        box = new BoxTile(++id);

        mouseT = new MouseTile(++id, AssetManager.getInstance().getSprite("mouseT", 9, 6));
        mouseB = new MouseTile(++id, AssetManager.getInstance().getSprite("mouseB", 9, 7));
        mouseL = new MouseTile(++id, AssetManager.getInstance().getSprite("mouseL", 8, 6));
        mouseR = new MouseTile(++id, AssetManager.getInstance().getSprite("mouseR", 8, 7));
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

    public boolean IsSolid() { return false; }
    public int GetId()       { return id;    }
}