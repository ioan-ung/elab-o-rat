package PaooGame.Tiles;

import PaooGame.Graphics.AssetManager;
import PaooGame.Graphics.Sprite;
import java.awt.*;
import java.awt.image.BufferedImage;

import static PaooGame.Graphics.Sprite.*;

public class Tile
{
    private static final int NO_TILES = 100;
    public static Tile[] tiles = new Tile[NO_TILES];

    public static void Init()
    {
        int id = -1; // Next is 0  | Floor tiles
        new FloorTile(++id, AssetManager.getSprite(FLOOR));
        new FloorTile(++id, AssetManager.getSprite(FLOOR_WIRE_HORIZONTAL));
        new FloorTile(++id, AssetManager.getSprite(FLOOR_WIRE_VERTICAL));
        new FloorTile(++id, AssetManager.getSprite(FLOOR_WIRE_SW));
        new FloorTile(++id, AssetManager.getSprite(FLOOR_WIRE_SE));
        new FloorTile(++id, AssetManager.getSprite(FLOOR_WIRE_NW));
        new FloorTile(++id, AssetManager.getSprite(FLOOR_WIRE_NE));

        id = 19; // Next is 20 | Closed/Open door tiles
        new DoorTile(++id,     AssetManager.getSprite(DOOR_R));
        new DoorTile(++id,     AssetManager.getSprite(DOOR_L));
        new DoorTile(++id,     AssetManager.getSprite(DOOR_T));
        new DoorTile(++id,     AssetManager.getSprite(DOOR_B));
        new DoorTile(++id,     AssetManager.getSprite(DOOR_NO_KEY_V));
        new DoorTile(++id,     AssetManager.getSprite(DOOR_NO_KEY_H));
        new OpenDoorTile(++id, AssetManager.getSprite(DOOR_R_OPEN));
        new OpenDoorTile(++id, AssetManager.getSprite(DOOR_L_OPEN));
        new OpenDoorTile(++id, AssetManager.getSprite(DOOR_T_OPEN));
        new OpenDoorTile(++id, AssetManager.getSprite(DOOR_B_OPEN));

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
