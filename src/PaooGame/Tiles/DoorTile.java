package PaooGame.Tiles;

import java.awt.image.BufferedImage;

/*! \class public class DoorTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip usa.
           Usa poate fi deschisa sau inchisa — solida doar cand e inchisa.
           Suporta mai multe variante vizuale (stanga, dreapta etc.)
 */
public class DoorTile extends Tile
{
    /*! Starea usii: true = inchisa (solida), false = deschisa */
    private boolean closed;

    /*! \fn public DoorTile(int id, BufferedImage img)
        \brief Constructorul de initializare al clasei.
               Usa porneste inchisa implicit.

        \param id  Id-ul dalei util in desenarea hartii.
        \param img Imaginea corespunzatoare variantei de usa.
     */
    public DoorTile(int id, BufferedImage img)
    {
        super(img, id);
        closed = true;
    }

    /*! \fn public boolean IsSolid()
        \brief Usa este solida doar cand e inchisa.
     */
    @Override
    public boolean IsSolid()
    {
        return closed;
    }

    /*! \fn public void open()
        \brief Deschide usa — playerul poate trece.
     */
    public void open()
    {
        closed = false;
    }

    /*! \fn public void close()
        \brief Inchide usa — blocheaza trecerea.
     */
    public void close()
    {
        closed = true;
    }

    /*! \fn public boolean isClosed()
        \brief Returneaza starea usii.
     */
    public boolean isClosed()
    {
        return closed;
    }
}