package PaooGame.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*! \class public class Assets
    \brief Clasa incarca toate elementele grafice necesare jocului O'Rat.
           Toate referintele sunt statice — incarcate o singura data in memorie.
 */
public class Assets
{
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
    public static BufferedImage playerLeft;
    public static BufferedImage playerRight;
    public static BufferedImage playerUp;
    public static BufferedImage playerDown;

    /*! \fn public static void Init()
        \brief Initializeaza toate referintele grafice.
               Trebuie apelata o singura data la pornirea jocului (in InitGame()).
     */
    public static void Init()
    {
        try
        {
            BufferedImage sheetImg = ImageIO.read(new File("maps/SpreetSheet.png"));
            System.out.println("[Assets] SpreetSheet incarcat: "
                    + sheetImg.getWidth() + "x" + sheetImg.getHeight());

            SpriteSheet sheet = new SpriteSheet(sheetImg);

            // --- Tile-uri harta ---
            floor  = sheet.crop(1, 7);// 1 7
            floorWireHorizontal = sheet.crop(1,6);
            floorWireVertical = sheet.crop(0,7);
            floorWireSW = sheet.crop(2,6);
            floorWireSE = sheet.crop(0,6);
            floorWireNW = sheet.crop(3,8);
            floorWireNE = sheet.crop(0,8);



            wall   = sheet.crop(6, 1);

            doorL   = sheet.crop(3, 6);
            doorR   = sheet.crop(4, 6);
            doorB   = sheet.crop(4, 7);
            doorT   = sheet.crop(4, 8);
            doorNoKeyV = sheet.crop(8, 4);
            doorNoKeyH = sheet.crop(8, 5);

            cheese = sheet.crop(9, 8);
            box = sheet.crop(8, 8);

            ratT = sheet.crop(9, 6);
            ratB = sheet.crop(9, 7);
            ratL = sheet.crop(8, 6);
            ratR = sheet.crop(8, 7);



            boxButtonWireTop = sheet.crop(2,5);
            boxButtonWireLeft = sheet.crop(1,4);
            boxButtonWireRight = sheet.crop(0,4);
            boxButtonWireBottom = sheet.crop(2,4);


            timedButtonWireTop = sheet.crop(6,5);
            timedButtonWireLeft = sheet.crop(5,4);
            timedButtonWireRight = sheet.crop(4,4);
            timedButtonWireBottom = sheet.crop(6,4);


            basicButtonWireTop = sheet.crop(2,7);
            basicButtonWireLeft = sheet.crop(1,8);
            basicButtonWireRight = sheet.crop(2,8);
            basicButtonWireBottom = sheet.crop(3,7);

        }
        catch(IOException e)
        {
            System.out.println("[Assets] EROARE: SpreetSheet.png nu a fost gasit!");
            e.printStackTrace();
        }
    }
}