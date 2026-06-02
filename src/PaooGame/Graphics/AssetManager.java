package PaooGame.Graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

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
    private static Map<String, BufferedImage> cache = new HashMap<>();
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

    public static BufferedImage getSprite(String name){
        return cache.get(name);
    }

    public static BufferedImage getSprite(String name, int col, int row){
        if(!cache.containsKey(name)){
            cache.put(name,SpriteSheetCutter.cropAndScale(col,row));
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
        SpriteSheetCutter.useSheet(new SpriteSheet(sheetImg),TILE_SIZE);

        getSprite("floor", 1, 7);
        getSprite("floorWireHorizontal", 1, 6);
        getSprite("floorWireVertical", 0, 7);
        getSprite("floorWireSW", 2, 6);
        getSprite("floorWireSE", 0, 6);
        getSprite("floorWireNW", 3, 8);
        getSprite("floorWireNE", 0, 8);



        getSprite("wall", 6, 1);

        // wall tiles — row 0
        getSprite("wallTile0",  0, 0); getSprite("wallTile1",  1, 0); getSprite("wallTile2",  2, 0);
        getSprite("wallTile3",  3, 0); getSprite("wallTile4",  4, 0); getSprite("wallTile5",  5, 0);
        getSprite("wallTile6",  6, 0); getSprite("wallTile7",  7, 0); getSprite("wallTile8",  8, 0);
        getSprite("wallTile9",  9, 0); getSprite("wallTile10", 10, 0);
        // wall tiles — row 1
        getSprite("wallTile11", 0, 1); getSprite("wallTile12", 1, 1); getSprite("wallTile13", 2, 1);
        getSprite("wallTile14", 3, 1); getSprite("wallTile15", 4, 1); getSprite("wallTile16", 5, 1);
        getSprite("wallTile17", 6, 1); getSprite("wallTile18", 7, 1); getSprite("wallTile19", 8, 1);
        getSprite("wallTile20", 9, 1); getSprite("wallTile21", 10, 1);
        // wall tiles — row 2
        getSprite("wallTile22", 0, 2); getSprite("wallTile23", 1, 2); getSprite("wallTile24", 2, 2);
        getSprite("wallTile25", 3, 2); getSprite("wallTile26", 4, 2); getSprite("wallTile27", 5, 2);
        getSprite("wallTile28", 6, 2); getSprite("wallTile29", 7, 2); getSprite("wallTile30", 8, 2);
        getSprite("wallTile31", 9, 2); getSprite("wallTile32", 10, 2);
        // wall tiles — row 3
        getSprite("wallTile33", 0, 3); getSprite("wallTile34", 1, 3); getSprite("wallTile35", 2, 3);
        getSprite("wallTile36", 3, 3); getSprite("wallTile37", 4, 3); getSprite("wallTile38", 5, 3);
        getSprite("wallTile39", 6, 3); getSprite("wallTile40", 7, 3); getSprite("wallTile41", 8, 3);
        getSprite("wallTile42", 9, 3); getSprite("wallTile43", 10, 3); getSprite("wallTile44", 11, 3);

        getSprite("doorR", 3, 6);
        getSprite("doorL", 4, 6);
        getSprite("doorB", 4, 7);
        getSprite("doorT", 4, 8);
        getSprite("doorNoKeyV", 8, 4);
        getSprite("doorNoKeyH", 8, 5);

        getSprite("doorROpen", 6, 6);
        getSprite("doorLOpen", 7, 6);
        getSprite("doorTOpen", 7, 8);
        getSprite("doorBOpen", 7, 7);

        getSprite("box", 8, 8);
        getSprite("cheese", 9, 8);
        getSprite("cat", 10, 8);




        getSprite("boxButtonWireTop", 2, 5);
        getSprite("boxButtonWireLeft", 1, 4);
        getSprite("boxButtonWireRight", 0, 4);
        getSprite("boxButtonWireBottom", 2, 4);


        getSprite("timedButtonWireTop", 6, 5);
        getSprite("timedButtonWireLeft", 5, 4);
        getSprite("timedButtonWireRight", 4, 4);
        getSprite("timedButtonWireBottom", 6, 4);


        getSprite("basicButtonWireTop", 2, 7);
        getSprite("basicButtonWireLeft", 1, 8);
        getSprite("basicButtonWireRight", 2, 8);
        getSprite("basicButtonWireBottom", 3, 7);


        mouseNorth     = getSprite("mouseNorth", 9, 6);
        mouseEast      = getSprite("mouseEast", 8, 7);
        mouseSouth     = getSprite("mouseSouth", 9, 7);
        mouseWest      = getSprite("mouseWest", 8, 6);

        mouseNorthEast = getSprite("mouseNorthEast", 10, 7);
        mouseNorthWest = getSprite("mouseNorthWest", 11, 7);
        mouseSouthEast = getSprite("mouseSouthEast", 10, 6);
        mouseSouthWest = getSprite("mouseSouthWest", 11, 6);
    }
}