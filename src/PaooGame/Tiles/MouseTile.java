package PaooGame.Tiles;

import java.awt.image.BufferedImage;

/*! \class public class MouseTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip soarece (player).
           Soarecele nu este solid — se poate misca pe orice tile de podea.
 */
public class MouseTile extends Tile
{
    /*! Directia curenta a soarecelui */
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private Direction direction;

    /*! \fn public MouseTile(int id, BufferedImage img)
        \brief Constructorul de initializare al clasei.

        \param id  Id-ul dalei util in desenarea hartii.
        \param img Imaginea corespunzatoare soarecelui.
     */
    public MouseTile(int id, BufferedImage img)
    {
        super(img, id);
        direction = Direction.DOWN;
    }

    /*! \fn public boolean IsSolid()
        \brief Soarecele nu este solid.
     */
    @Override
    public boolean IsSolid()
    {
        return false;
    }

    /*! \fn public void setDirection(Direction dir)
        \brief Seteaza directia soarecelui.
     */
    public void setDirection(Direction dir)
    {
        direction = dir;
    }

    /*! \fn public Direction getDirection()
        \brief Returneaza directia curenta a soarecelui.
     */
    public Direction getDirection()
    {
        return direction;
    }
}
