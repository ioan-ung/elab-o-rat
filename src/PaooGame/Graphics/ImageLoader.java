package PaooGame.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

/*! \class public class ImageLoader
    \brief Clasa ce contine o metoda statica pentru incarcarea unei imagini in memorie.
 */
public class ImageLoader
{
    /*! \fn  public static BufferedImage loadImag`e(String path)
        \brief Incarca o imagine intr-un obiect BufferedImage si returneaza o referinta catre acesta.

        \param path Calea relativa pentru localizarea fisierul imagine.
     */
    public static BufferedImage LoadImage(String path)
    {
            /// Avand in vedere exista situatii in care fisierul sursa sa nu poate fi accesat
            /// metoda read() arunca o excpetie ce trebuie tratata
        try
        {
            java.net.URL url = ImageLoader.class.getResource(path);
            if (url == null) {
                System.err.println("[ImageLoader] Resource not found on classpath: " + path);
                return null;
            }
            return ImageIO.read(url);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }}

