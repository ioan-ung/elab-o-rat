package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class public class DoorTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip usa.
           Usa poate fi deschisa sau inchisa — solida doar cand e inchisa.
 */
public class DoorTile extends Tile
{
    /*! Starea usii: true = inchisa (solida), false = deschisa */
    private boolean closed;

    /*! \fn public DoorTile(int id)
        \brief Constructorul de initializare al clasei.
               Usa porneste inchisa implicit.

        \param id Id-ul dalei util in desenarea hartii.
     */
    public DoorTile(int id)
    {
        super(Assets.door, id);
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