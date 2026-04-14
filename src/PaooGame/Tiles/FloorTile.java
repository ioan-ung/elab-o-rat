package PaooGame.Tiles;

import java.awt.image.BufferedImage;

/*! \class public class FloorTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip podea.
           Playerul poate merge pe aceasta dala (nu e solida).
           Suporta mai multe variante vizuale (podea normala, cu fire etc.)
 */
public class FloorTile extends Tile
{
    /*! \fn public FloorTile(int id, BufferedImage img)
        \brief Constructorul de initializare al clasei.

        \param id  Id-ul dalei util in desenarea hartii.
        \param img Imaginea corespunzatoare variantei de podea.
     */
    public FloorTile(int id, BufferedImage img)
    {
        super(img, id);
    }

    /*! \fn public boolean IsSolid()
        \brief Podeaua nu este solida — playerul poate merge pe ea.
     */
    @Override
    public boolean IsSolid()
    {
        return false;
    }
}