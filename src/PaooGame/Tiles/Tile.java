package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile
{
    private static final int NO_TILES = 100;
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


    public static Tile floor;
    public static Tile floorWireHorizontal;
    public static Tile floorWireVertical;
    public static Tile floorWireSW;
    public static Tile floorWireSE;
    public static Tile floorWireNW;
    public static Tile floorWireNE;


    public static Tile wallTile0, wallTile1, wallTile2, wallTile3, wallTile4, wallTile5, wallTile6, wallTile7, wallTile8, wallTile9;
    public static Tile wallTile10, wallTile11, wallTile12, wallTile13, wallTile14, wallTile15, wallTile16, wallTile17, wallTile18, wallTile19;
    public static Tile wallTile20, wallTile21, wallTile22, wallTile23, wallTile24, wallTile25, wallTile26, wallTile27, wallTile28, wallTile29;
    public static Tile wallTile30, wallTile31, wallTile32, wallTile33, wallTile34, wallTile35, wallTile36, wallTile37, wallTile38, wallTile39;
    public static Tile wallTile40, wallTile41, wallTile42, wallTile43, wallTile44;

    public static void Init()
    {
        int id = -1; // Next is 0  | Floor tiles
        floor  = new FloorTile(++id, AssetManager.floor);
        floorWireHorizontal = new FloorTile(++id, AssetManager.floorWireHorizontal);
        floorWireVertical = new FloorTile(++id, AssetManager.floorWireVertical);
        floorWireSW = new FloorTile(++id, AssetManager.floorWireSW);
        floorWireSE = new FloorTile(++id, AssetManager.floorWireSE);
        floorWireNW = new FloorTile(++id, AssetManager.floorWireNW);
        floorWireNE = new FloorTile(++id, AssetManager.floorWireNE);

        id = 19; // Next is 20 | Closed/Open door tiles
        doorR = new DoorTile(++id, AssetManager.doorR);
        doorL = new DoorTile(++id, AssetManager.doorL);
        doorT   = new DoorTile(++id, AssetManager.doorT);
        doorB   = new DoorTile(++id, AssetManager.doorB);
        doorNoKeyV  = new DoorTile(++id, AssetManager.doorNoKeyV);
        doorNoKeyH  = new DoorTile(++id, AssetManager.doorNoKeyH);
        doorROpen = new OpenDoorTile(++id, AssetManager.doorROpen);
        doorLOpen = new OpenDoorTile(++id, AssetManager.doorLOpen);
        doorTOpen   = new OpenDoorTile(++id, AssetManager.doorTOpen);
        doorBOpen   = new OpenDoorTile(++id, AssetManager.doorBOpen);

        id = 39; // Next is 40 | Wall tiles
        wallTile0 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile0", 0, 0));
        wallTile1 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile1", id-40, 0));
        wallTile2 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile2", id-40, 0));
        wallTile3 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile3", id-40, 0));
        wallTile4 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile4", id-40, 0));
        wallTile5 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile5", id-40, 0));
        wallTile6 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile6", id-40, 0));
        wallTile7 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile7", id-40, 0));
        wallTile8 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile8", id-40, 0));
        wallTile9 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile9", id-40, 0));
        wallTile10 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile10", id-40, 0));
        // Next id is 51
        wallTile11 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile11", 0, 1));
        wallTile12 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile13", id-51, 1));
        wallTile13 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile13", id-51, 1));
        wallTile14 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile14", id-51, 1));
        wallTile15 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile15", id-51, 1));
        wallTile16 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile16", id-51, 1));
        wallTile17 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile17", id-51, 1));
        wallTile18 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile18", id-51, 1));
        wallTile19 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile19", id-51, 1));
        wallTile20 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile20", id-51, 1));
        wallTile21 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile21", id-51, 1));
        // Next id is 62
        wallTile22 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile22", 0, 2));
        wallTile23 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile23", id-62, 2));
        wallTile24 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile24", id-62, 2));
        wallTile25 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile25", id-62, 2));
        wallTile26 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile26", id-62, 2));
        wallTile27 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile27", id-62, 2));
        wallTile28 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile28", id-62, 2));
        wallTile29 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile29", id-62, 2));
        wallTile30 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile30", id-62, 2));
        wallTile31 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile31", id-62, 2));
        wallTile32 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile32", id-62, 2));
        // Next id is 73
        wallTile33 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile33", 0, 3));
        wallTile34 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile34", id-73, 3));
        wallTile35 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile35", id-73, 3));
        wallTile36 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile36", id-73, 3));
        wallTile37 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile37", id-73, 3));
        wallTile38 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile38", id-73, 3));
        wallTile39 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile39", id-73, 3));
        wallTile40 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile40", id-73, 3));
        wallTile41 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile41", id-73, 3));
        wallTile42 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile42", id-73, 3));
        wallTile43 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile43", id-73, 3));
        wallTile44 = new WallTile(++id, AssetManager.getInstance().getSprite("wallTile44", id-73, 3));
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
    public boolean IsBoxSolid()
    {
        return false;
    }
    public int GetId()       { return id;    }
}