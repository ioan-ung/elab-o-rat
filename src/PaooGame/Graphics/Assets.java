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
    public static BufferedImage wall;
    public static BufferedImage door;
    public static BufferedImage cheese;

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
            wall   = sheet.crop(1, 1);
            door   = sheet.crop(3, 6);// 3 6
            cheese = sheet.crop(9, 8);//9 8

            // --- Sprite-uri player ---
//            playerLeft  = sheet.crop(8, 6);
//            playerRight = sheet.crop(8, 7);
//            playerUp    = sheet.crop(9, 6);
//            playerDown  = sheet.crop(9, 7);
        }
        catch(IOException e)
        {
            System.out.println("[Assets] EROARE: SpreetSheet.png nu a fost gasit!");
            e.printStackTrace();
        }
    }
}