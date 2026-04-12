package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class public class FloorTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip podea.
           Playerul poate merge pe aceasta dala (nu e solida).
 */
public class FloorTile extends Tile
{
    /*! \fn public FloorTile(int id)
        \brief Constructorul de initializare al clasei.

        \param id Id-ul dalei util in desenarea hartii.
     */
    public FloorTile(int id)
    {
        super(Assets.floor, id);
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