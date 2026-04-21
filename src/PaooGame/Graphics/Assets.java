package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca toate elementele grafice necesare jocului O'Rat.
           Toate referintele sunt statice — incarcate o singura data in memorie.
 */
public class Assets
{
    // Size and scale of the tiles
    public static final int    TILE_ACTUAL_SIZE = 32;
    public static final int    SCALE = 2;
    public static final int    TILE_SIZE = TILE_ACTUAL_SIZE*SCALE;
    /*! Referinte catre tile-urile hartii */
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

    public static BufferedImage ratT;
    public static BufferedImage ratB;
    public static BufferedImage ratL;
    public static BufferedImage ratR;

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

    /*! Referinte catre sprite-urile playerului */
    public static BufferedImage playerWest, playerEast, playerNorth, playerNorthWest, playerNorthEast, playerSouth, playerSouthWest, playerSouthEast;

    /*! \fn public static void Init()
        \brief Initializeaza toate referintele grafice.
               Trebuie apelata o singura data la pornirea jocului (in InitGame()).
     */
    public static void Init()
    {

        BufferedImage sheetImg = ImageLoader.LoadImage("/textures/SpriteSheet.png");
        System.out.println("[Assets] SpriteSheet incarcat: "
                            + sheetImg.getWidth()
                            + "x"
                            + sheetImg.getHeight()
                            );
        SpriteSheetCutter.useSheet(new SpriteSheet(sheetImg),TILE_SIZE);

        floor  = SpriteSheetCutter.cropAndScale(1, 7);// 1 7
        floorWireHorizontal = SpriteSheetCutter.cropAndScale(1,6);
        floorWireVertical = SpriteSheetCutter.cropAndScale(0,7);
        floorWireSW = SpriteSheetCutter.cropAndScale(2,6);
        floorWireSE = SpriteSheetCutter.cropAndScale(0,6);
        floorWireNW = SpriteSheetCutter.cropAndScale(3,8);
        floorWireNE = SpriteSheetCutter.cropAndScale(0,8);



        wall   = SpriteSheetCutter.cropAndScale(6, 1);

        doorL   = SpriteSheetCutter.cropAndScale(3, 6);
        doorR   = SpriteSheetCutter.cropAndScale(4, 6);
        doorB   = SpriteSheetCutter.cropAndScale(4, 7);
        doorT   = SpriteSheetCutter.cropAndScale(4, 8);
        doorNoKeyV = SpriteSheetCutter.cropAndScale(8, 4);
        doorNoKeyH = SpriteSheetCutter.cropAndScale(8, 5);

        cheese = SpriteSheetCutter.cropAndScale(9, 8);
        box = SpriteSheetCutter.cropAndScale(8, 8);

        ratT = SpriteSheetCutter.cropAndScale(9, 6);
        ratB = SpriteSheetCutter.cropAndScale(9, 7);
        ratL = SpriteSheetCutter.cropAndScale(8, 6);
        ratR = SpriteSheetCutter.cropAndScale(8, 7);



        boxButtonWireTop = SpriteSheetCutter.cropAndScale(2,5);
        boxButtonWireLeft = SpriteSheetCutter.cropAndScale(1,4);
        boxButtonWireRight = SpriteSheetCutter.cropAndScale(0,4);
        boxButtonWireBottom = SpriteSheetCutter.cropAndScale(2,4);


        timedButtonWireTop = SpriteSheetCutter.cropAndScale(6,5);
        timedButtonWireLeft = SpriteSheetCutter.cropAndScale(5,4);
        timedButtonWireRight = SpriteSheetCutter.cropAndScale(4,4);
        timedButtonWireBottom = SpriteSheetCutter.cropAndScale(6,4);


        basicButtonWireTop = SpriteSheetCutter.cropAndScale(2,7);
        basicButtonWireLeft = SpriteSheetCutter.cropAndScale(1,8);
        basicButtonWireRight = SpriteSheetCutter.cropAndScale(2,8);
        basicButtonWireBottom = SpriteSheetCutter.cropAndScale(3,7);


        playerNorth = SpriteSheetCutter.cropAndScale(9,6);
        playerEast = SpriteSheetCutter.cropAndScale(8,7);
        playerSouth = SpriteSheetCutter.cropAndScale(9,7);
        playerWest = SpriteSheetCutter.cropAndScale(8,6);
        playerNorthEast = SpriteSheetCutter.cropAndScale(10,7);
        playerNorthWest = SpriteSheetCutter.cropAndScale(11,7);
        playerSouthEast = SpriteSheetCutter.cropAndScale(10,6);
        playerSouthWest = SpriteSheetCutter.cropAndScale(11,6);
    }
}