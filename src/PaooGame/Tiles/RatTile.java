package PaooGame.Tiles;

import java.awt.image.BufferedImage;

/*! \class public class RatTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip sobolan (player).
           Sobolanul nu este solid — se poate misca pe orice tile de podea.
 */
public class RatTile extends Tile
{
    /*! Directia curenta a sobolanului */
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private Direction direction;

    /*! \fn public RatTile(int id, BufferedImage img)
        \brief Constructorul de initializare al clasei.

        \param id  Id-ul dalei util in desenarea hartii.
        \param img Imaginea corespunzatoare sobolanului.
     */
    public RatTile(int id, BufferedImage img)
    {
        super(img, id);
        direction = Direction.DOWN;
    }

    /*! \fn public boolean IsSolid()
        \brief Sobolanul nu este solid.
     */
    @Override
    public boolean IsSolid()
    {
        return false;
    }

    /*! \fn public void setDirection(Direction dir)
        \brief Seteaza directia sobolanului.
     */
    public void setDirection(Direction dir)
    {
        direction = dir;
    }

    /*! \fn public Direction getDirection()
        \brief Returneaza directia curenta a sobolanului.
     */
    public Direction getDirection()
    {
        return direction;
    }
}