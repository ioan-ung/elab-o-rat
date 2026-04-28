package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile
{
    private static final int NO_TILES = 500;
    public static Tile[] tiles = new Tile[NO_TILES];

    public static Tile wallTile;
    public static Tile doorL;
    public static Tile doorR;
    public static Tile doorT;
    public static Tile doorB;
    public static Tile doorNoKeyV;
    public static Tile doorNoKeyH;


    public static Tile cheese;
    public static Tile box;
    public static Tile mouseT;
    public static Tile mouseB;
    public static Tile mouseL;
    public static Tile mouseR;


    public static Tile basicButtonWireTop;
    public static Tile basicButtonWireLeft;
    public static Tile basicButtonWireRight;
    public static Tile basicButtonWireBottom;

    public static Tile timedButtonWireTop;
    public static Tile timedButtonWireLeft;
    public static Tile timedButtonWireRight;
    public static Tile timedButtonWireBottom;

    public static Tile boxButtonWireTop;
    public static Tile boxButtonWireLeft;
    public static Tile boxButtonWireRight;
    public static Tile boxButtonWireBottom;

    public static Tile floor;
    public static Tile floorWireHorizontal;
    public static Tile floorWireVertical;
    public static Tile floorWireSW;
    public static Tile floorWireSE;
    public static Tile floorWireNW;
    public static Tile floorWireNE;

    public static void Init()
    {
        floor  = new FloorTile(0, AssetManager.getInstance().getSprite("floor", 1, 7));
        floorWireHorizontal = new FloorTile(1, AssetManager.getInstance().getSprite("floorWireHorizontal", 1, 6));
        floorWireVertical = new FloorTile(2, AssetManager.getInstance().getSprite("floorWireVertical", 0, 7));
        floorWireSW = new FloorTile(3, AssetManager.getInstance().getSprite("floorWireSW", 2, 6));
        floorWireSE = new FloorTile(4, AssetManager.getInstance().getSprite("floorWireSE", 0, 6));
        floorWireNW = new FloorTile(5, AssetManager.getInstance().getSprite("floorWireNW", 3, 8));
        floorWireNE = new FloorTile(6, AssetManager.getInstance().getSprite("floorWireNE", 0, 8));

        wallTile   = new WallTile(10);


        doorL   = new DoorTile(20, AssetManager.getInstance().getSprite("doorL", 3, 6));
        doorR   = new DoorTile(21, AssetManager.getInstance().getSprite("doorR", 4, 6));
        doorT   = new DoorTile(22, AssetManager.getInstance().getSprite("doorT", 4, 8));
        doorB   = new DoorTile(23, AssetManager.getInstance().getSprite("doorB", 4, 7));
        doorNoKeyV  = new DoorTile(24, AssetManager.getInstance().getSprite("doorNoKeyV", 8, 4));
        doorNoKeyH  = new DoorTile(25, AssetManager.getInstance().getSprite("doorNoKeyH", 8, 5));

        boxButtonWireTop = new ButtonTile(30, AssetManager.getInstance().getSprite("boxButtonWireTop", 2, 5));
        boxButtonWireLeft = new ButtonTile(31, AssetManager.getInstance().getSprite("boxButtonWireLeft", 1, 4));
        boxButtonWireRight = new ButtonTile(32, AssetManager.getInstance().getSprite("boxButtonWireRight", 0, 4));
        boxButtonWireBottom = new ButtonTile(33, AssetManager.getInstance().getSprite("boxButtonWireBottom", 2, 4));

        timedButtonWireTop = new ButtonTile(34, AssetManager.getInstance().getSprite("timedButtonWireTop", 6, 5));
        timedButtonWireLeft = new ButtonTile(35, AssetManager.getInstance().getSprite("timedButtonWireLeft", 5, 4));
        timedButtonWireRight = new ButtonTile(36, AssetManager.getInstance().getSprite("timedButtonWireRight", 4, 4));
        timedButtonWireBottom = new ButtonTile(37, AssetManager.getInstance().getSprite("timedButtonWireBottom", 6, 4));

        basicButtonWireTop = new ButtonTile(40, AssetManager.getInstance().getSprite("basicButtonWireTop", 2, 7));
        basicButtonWireLeft = new ButtonTile(41, AssetManager.getInstance().getSprite("basicButtonWireLeft", 1, 8));
        basicButtonWireRight = new ButtonTile(42, AssetManager.getInstance().getSprite("basicButtonWireRight", 2, 8));
        basicButtonWireBottom = new ButtonTile(43, AssetManager.getInstance().getSprite("basicButtonWireBottom", 3, 7));

        cheese = new CheeseTile(50);
        box = new BoxTile(51);

        mouseT = new MouseTile(52, AssetManager.getInstance().getSprite("mouseT", 9, 6));
        mouseB = new MouseTile(53, AssetManager.getInstance().getSprite("mouseB", 9, 7));
        mouseL = new MouseTile(54, AssetManager.getInstance().getSprite("mouseL", 8, 6));
        mouseR = new MouseTile(55, AssetManager.getInstance().getSprite("mouseR", 8, 7));

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