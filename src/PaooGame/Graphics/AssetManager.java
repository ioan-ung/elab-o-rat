package PaooGame.Graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static PaooGame.Graphics.Sprite.*;

/*! \class public class Assets
    \brief Clasa incarca toate elementele grafice necesare jocului O'Rat.
           Toate referintele sunt statice — incarcate o singura data in memorie.
 */
public class AssetManager
{
    // Size and scale of the tiles
    public static final int    TILE_ACTUAL_SIZE = 32;
    public static final int    SCALE = 2;
    public static final int    TILE_SIZE = TILE_ACTUAL_SIZE*SCALE;
    private static final Map<String, BufferedImage> cache = new HashMap<>();
    private static AssetManager instance;    //singleton

    /*! Referinte catre sprite-urile mouseului */
    public static BufferedImage mouseWest, mouseEast, mouseNorth, mouseNorthWest, mouseNorthEast, mouseSouth, mouseSouthWest, mouseSouthEast;

    private AssetManager(){}; //constructor private--conventie singleton

    /*
        getter folosit pt a lua instanta singleton
    */
    public static AssetManager getInstance(){
        if(instance==null){
            instance=new AssetManager();
        }
        return instance;
    }

    public static BufferedImage getSprite(Sprite sprite){
        return cache.get(sprite.key);
    }

    public static BufferedImage getSprite(String name){
        return cache.get(name);
    }

    public static BufferedImage getSprite(Sprite sprite, int col, int row){
        return getSprite(sprite.key, col, row);
    }

    public static BufferedImage getSprite(String name, int col, int row){
        if(!cache.containsKey(name)){
            cache.put(name, SpriteSheetCutter.cropAndScale(col, row));
        }
        return cache.get(name);
    }

    public static void Init()
    {
        getInstance();
        BufferedImage sheetImg = ImageLoader.LoadImage("/textures/SpriteSheet.png");
        System.out.println("[Assets] SpriteSheet incarcat: "
                            + sheetImg.getWidth()
                            + "x"
                            + sheetImg.getHeight()
                            );
        SpriteSheetCutter.useSheet(new SpriteSheet(sheetImg), TILE_SIZE);

        getSprite(FLOOR,               1, 7);
        getSprite(FLOOR_WIRE_HORIZONTAL, 1, 6);
        getSprite(FLOOR_WIRE_VERTICAL, 0, 7);
        getSprite(FLOOR_WIRE_SW,       2, 6);
        getSprite(FLOOR_WIRE_SE,       0, 6);
        getSprite(FLOOR_WIRE_NW,       3, 8);
        getSprite(FLOOR_WIRE_NE,       0, 8);

        getSprite(WALL, 6, 1);

        // wall tiles — 11 per row, 4 rows (0-44)
        for (int i = 0; i <= 44; i++)
            getSprite("wallTile" + i, i % 11, i / 11);

        getSprite(DOOR_R,       3, 6);
        getSprite(DOOR_L,       4, 6);
        getSprite(DOOR_B,       4, 7);
        getSprite(DOOR_T,       4, 8);
        getSprite(DOOR_NO_KEY_V, 8, 4);
        getSprite(DOOR_NO_KEY_H, 8, 5);

        getSprite(DOOR_R_OPEN,  6, 6);
        getSprite(DOOR_L_OPEN,  7, 6);
        getSprite(DOOR_T_OPEN,  7, 8);
        getSprite(DOOR_B_OPEN,  7, 7);

        getSprite(BOX,    8, 8);
        getSprite(CHEESE, 9, 8);
        getSprite(CAT,    10, 8);

        getSprite(BOX_BUTTON_WIRE_TOP,    2, 5);
        getSprite(BOX_BUTTON_WIRE_LEFT,   1, 4);
        getSprite(BOX_BUTTON_WIRE_RIGHT,  0, 4);
        getSprite(BOX_BUTTON_WIRE_BOTTOM, 2, 4);

        getSprite(TIMED_BUTTON_WIRE_TOP,    6, 5);
        getSprite(TIMED_BUTTON_WIRE_LEFT,   5, 4);
        getSprite(TIMED_BUTTON_WIRE_RIGHT,  4, 4);
        getSprite(TIMED_BUTTON_WIRE_BOTTOM, 6, 4);

        getSprite(BASIC_BUTTON_WIRE_TOP,    2, 7);
        getSprite(BASIC_BUTTON_WIRE_LEFT,   1, 8);
        getSprite(BASIC_BUTTON_WIRE_RIGHT,  2, 8);
        getSprite(BASIC_BUTTON_WIRE_BOTTOM, 3, 7);

        mouseNorth     = getSprite(MOUSE_NORTH,      9,  6);
        mouseEast      = getSprite(MOUSE_EAST,        8,  7);
        mouseSouth     = getSprite(MOUSE_SOUTH,       9,  7);
        mouseWest      = getSprite(MOUSE_WEST,        8,  6);
        mouseNorthEast = getSprite(MOUSE_NORTH_EAST, 10,  7);
        mouseNorthWest = getSprite(MOUSE_NORTH_WEST, 11,  7);
        mouseSouthEast = getSprite(MOUSE_SOUTH_EAST, 10,  6);
        mouseSouthWest = getSprite(MOUSE_SOUTH_WEST, 11,  6);
    }
}
