package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class public class WallTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip perete.
           Playerul nu poate trece prin aceasta dala (e solida).
 */
public class WallTile extends Tile
{
    /*! \fn public WallTile(int id)
        \brief Constructorul de initializare al clasei.

        \param id Id-ul dalei util in desenarea hartii.
     */
    public WallTile(int id)
    {
        super(Assets.wall, id);
    }

    /*! \fn public boolean IsSolid()
        \brief Peretele este solid — blocheaza miscarea playerului.
     */
    @Override
    public boolean IsSolid()
    {
        return true;
    }
}