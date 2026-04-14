package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

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
    public static Tile ratT;
    public static Tile ratB;
    public static Tile ratL;
    public static Tile ratR;


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
        floor  = new FloorTile(0,Assets.floor);
        floorWireHorizontal = new FloorTile(1,Assets.floorWireHorizontal);
        floorWireVertical = new FloorTile(2,Assets.floorWireVertical);
        floorWireSW = new FloorTile(3,Assets.floorWireSW);
        floorWireSE = new FloorTile(4,Assets.floorWireSE);
        floorWireNW = new FloorTile(5,Assets.floorWireNW);
        floorWireNE = new FloorTile(6,Assets.floorWireNE);

        wallTile   = new WallTile(10);


        doorL   = new DoorTile(20,Assets.doorL);
        doorR   = new DoorTile(21,Assets.doorR);
        doorT   = new DoorTile(22,Assets.doorT);
        doorB   = new DoorTile(23,Assets.doorB);
        doorNoKeyV  = new DoorTile(24,Assets.doorNoKeyV);
        doorNoKeyH  = new DoorTile(25,Assets.doorNoKeyH);

        boxButtonWireTop = new ButtonTile(30, Assets.boxButtonWireTop);
        boxButtonWireLeft = new ButtonTile(31,Assets.boxButtonWireLeft);
        boxButtonWireRight = new ButtonTile(32,Assets.boxButtonWireRight);
        boxButtonWireBottom = new ButtonTile(33,Assets.boxButtonWireBottom);

        timedButtonWireTop = new ButtonTile(34, Assets.timedButtonWireTop);
        timedButtonWireLeft = new ButtonTile(35, Assets.timedButtonWireLeft);
        timedButtonWireRight = new ButtonTile(36, Assets.timedButtonWireRight);
        timedButtonWireBottom = new ButtonTile(37, Assets.timedButtonWireBottom);

        basicButtonWireTop = new ButtonTile(40, Assets.basicButtonWireTop);
        basicButtonWireLeft = new ButtonTile(41, Assets.basicButtonWireLeft);
        basicButtonWireRight = new ButtonTile(42, Assets.basicButtonWireRight);
        basicButtonWireBottom = new ButtonTile(43, Assets.basicButtonWireBottom);

        cheese = new CheeseTile(50);
        box = new BoxTile(51);

        ratT = new RatTile(52,Assets.ratT);
        ratB = new RatTile(53,Assets.ratB);
        ratL = new RatTile(54,Assets.ratL);
        ratR = new RatTile(55,Assets.ratR);

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