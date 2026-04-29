package PaooGame.Tiles;
import java.awt.image.BufferedImage;

// dala de tip buton — nu e solida, poate fi apasata/resetata
public class ButtonTile extends Tile
{
    // true = apasat
    private boolean pressed;

    public ButtonTile(int id, BufferedImage img)
    {
        super(img, id);
        pressed = false;
    }

    @Override
    public boolean IsSolid()
    {
        return false;
    }

    public void press()
    {
        pressed = true;
    }

    public void reset()
    {
        pressed = false;
    }

    public boolean isPressed()
    {
        return pressed;
    }
}