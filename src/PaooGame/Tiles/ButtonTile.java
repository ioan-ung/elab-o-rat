package PaooGame.Tiles;
import java.awt.image.BufferedImage;
import PaooGame.Graphics.Assets;

/*! \class public class ButtonTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip buton.
           Butonul poate fi apasat sau nu — nu este solid.
 */
public class ButtonTile extends Tile
{
    /*! Starea butonului: true = apasat, false = neapasat */
    private boolean pressed;

    /*! \fn public ButtonTile(int id)
        \brief Constructorul de initializare al clasei.
               Butonul porneste neapasat implicit.

        \param id Id-ul dalei util in desenarea hartii.
     */
    public ButtonTile(int id, BufferedImage img)  // ← adaugi img
    {
        super(img, id);  // ← pasezi img în loc de Assets.button
        pressed = false;
    }


    /*! \fn public boolean IsSolid()
        \brief Butonul nu este solid — playerul poate merge pe el.
     */
    @Override
    public boolean IsSolid()
    {
        return false;
    }

    /*! \fn public void press()
        \brief Apasa butonul.
     */
    public void press()
    {
        pressed = true;
    }

    /*! \fn public void reset()
        \brief Reseteaza butonul la starea initiala.
     */
    public void reset()
    {
        pressed = false;
    }

    /*! \fn public boolean isPressed()
        \brief Returneaza daca butonul e apasat.
     */
    public boolean isPressed()
    {
        return pressed;
    }
}