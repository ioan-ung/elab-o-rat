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
    private Map<String, BufferedImage> cache = new HashMap<>();
    /*! Referinte catre tile-urile hartii */

    private static AssetManager instance;    //singleton
    public static BufferedImage floor;
    public static BufferedImage floorWireHorizontal;
    public static BufferedImage floorWireVertical;
    public static BufferedImage floorWireSW;
    public static BufferedImage floorWireSE;
    public static BufferedImage floorWireNW;
    public static BufferedImage floorWireNE;



    public static BufferedImage wall;
    public static BufferedImage doorL;
    public static BufferedImage doorR;
    public static BufferedImage doorT;
    public static BufferedImage doorB;
    public static BufferedImage doorNoKeyV;
    public static BufferedImage doorNoKeyH;


    public static BufferedImage cheese;
    public static BufferedImage box;

    public static BufferedImage basicButtonWireTop;
    public static BufferedImage basicButtonWireLeft;
    public static BufferedImage basicButtonWireRight;
    public static BufferedImage basicButtonWireBottom;

    public static BufferedImage timedButtonWireTop;
    public static BufferedImage timedButtonWireLeft;
    public static BufferedImage timedButtonWireRight;
    public static BufferedImage timedButtonWireBottom;

    public static BufferedImage boxButtonWireTop;
    public static BufferedImage boxButtonWireLeft;
    public static BufferedImage boxButtonWireRight;
    public static BufferedImage boxButtonWireBottom;

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

    public BufferedImage getSprite(String name, int col, int row){
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

        floor = AssetManager.getInstance().getSprite("floor", 1, 7);
        floorWireHorizontal = AssetManager.getInstance().getSprite("floorWireHorizontal", 1, 6);
        floorWireVertical = AssetManager.getInstance().getSprite("floorWireVertical", 0, 7);
        floorWireSW = AssetManager.getInstance().getSprite("floorWireSW", 2, 6);
        floorWireSE = AssetManager.getInstance().getSprite("floorWireSE", 0, 6);
        floorWireNW = AssetManager.getInstance().getSprite("floorWireNW", 3, 8);
        floorWireNE = AssetManager.getInstance().getSprite("floorWireNE", 0, 8);



        wall   = AssetManager.getInstance().getSprite("wall", 6, 1);

        doorR   = AssetManager.getInstance().getSprite("doorR", 3, 6);
        doorL   = AssetManager.getInstance().getSprite("doorL", 4, 6);
        doorB   = AssetManager.getInstance().getSprite("doorB", 4, 7);
        doorT   = AssetManager.getInstance().getSprite("doorT", 4, 8);
        doorNoKeyV = AssetManager.getInstance().getSprite("doorNoKeyV", 8, 4);
        doorNoKeyH = AssetManager.getInstance().getSprite("doorNoKeyH", 8, 5);

        cheese = AssetManager.getInstance().getSprite("cheese", 9, 8);
        box = AssetManager.getInstance().getSprite("box", 8, 8);




        boxButtonWireTop = AssetManager.getInstance().getSprite("boxButtonWireTop", 2, 5);
        boxButtonWireLeft = AssetManager.getInstance().getSprite("boxButtonWireLeft", 1, 4);
        boxButtonWireRight = AssetManager.getInstance().getSprite("boxButtonWireRight", 0, 4);
        boxButtonWireBottom = AssetManager.getInstance().getSprite("boxButtonWireBottom", 2, 4);


        timedButtonWireTop = AssetManager.getInstance().getSprite("timedButtonWireTop", 6, 5);
        timedButtonWireLeft = AssetManager.getInstance().getSprite("timedButtonWireLeft", 5, 4);
        timedButtonWireRight = AssetManager.getInstance().getSprite("timedButtonWireRight", 4, 4);
        timedButtonWireBottom = AssetManager.getInstance().getSprite("timedButtonWireBottom", 6, 4);


        basicButtonWireTop = AssetManager.getInstance().getSprite("basicButtonWireTop", 2, 7);
        basicButtonWireLeft = AssetManager.getInstance().getSprite("basicButtonWireLeft", 1, 8);
        basicButtonWireRight = AssetManager.getInstance().getSprite("basicButtonWireRight", 2, 8);
        basicButtonWireBottom = AssetManager.getInstance().getSprite("basicButtonWireBottom", 3, 7);


        mouseNorth = AssetManager.getInstance().getSprite("mouseNorth", 9, 6);
        mouseEast = AssetManager.getInstance().getSprite("mouseEast", 8, 7);
        mouseSouth = AssetManager.getInstance().getSprite("mouseSouth", 9, 7);
        mouseWest = AssetManager.getInstance().getSprite("mouseWest", 8, 6);
        mouseNorthEast = AssetManager.getInstance().getSprite("mouseNorthEast", 10, 7);
        mouseNorthWest = AssetManager.getInstance().getSprite("mouseNorthWest", 11, 7);
        mouseSouthEast = AssetManager.getInstance().getSprite("mouseSouthEast", 10, 6);
        mouseSouthWest = AssetManager.getInstance().getSprite("mouseSouthWest", 11, 6);
    }
}