package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class public class BoxTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip cutie.
           Cutia este solida si poate fi impinsa de player.
 */
public class BoxTile extends Tile
{
    /*! \fn public BoxTile(int id)
        \brief Constructorul de initializare al clasei.

        \param id Id-ul dalei util in desenarea hartii.
     */
    public BoxTile(int id)
    {
        super(Assets.box, id);
    }

    /*! \fn public boolean IsSolid()
        \brief Cutia este solida — blocheaza miscarea playerului.
     */
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}