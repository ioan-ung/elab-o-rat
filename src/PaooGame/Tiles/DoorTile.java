package PaooGame.Tiles;

import java.awt.image.BufferedImage;

/*! \class public class DoorTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip usa inchisa.
           Suporta mai multe variante vizuale (stanga, dreapta etc.)
 */
public class DoorTile extends Tile
{
    /*! \fn public DoorTile(int id, BufferedImage img)
        \brief Constructorul de initializare al clasei.

        \param id  Id-ul dalei util in desenarea hartii.
        \param img Imaginea corespunzatoare variantei de usa.
     */
    public DoorTile(int id, BufferedImage img)
    {
        super(img, id);
    }

    /*! \fn public boolean IsSolid()
        \brief Usa inchisa este solida.
     */
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}