package PaooGame;

/*! \class Camera
    \brief Gestioneaza viewport-ul jocului.
           Camera urmareste playerul si calculeaza offsetul de desenare.
 */
public class Camera
{
    private float xOffset;  /*!< Offsetul pe X al camerei */
    private float yOffset;  /*!< Offsetul pe Y al camerei */

    private final int mapWidthPx;   /*!< Latimea hartii in pixeli */
    private final int mapHeightPx;  /*!< Inaltimea hartii in pixeli */
    private final int wndWidth;     /*!< Latimea ferestrei */
    private final int wndHeight;    /*!< Inaltimea ferestrei */

    public Camera(int wndWidth, int wndHeight, int mapWidthPx, int mapHeightPx)
    {
        this.wndWidth    = wndWidth;
        this.wndHeight   = wndHeight;
        this.mapWidthPx  = mapWidthPx;
        this.mapHeightPx = mapHeightPx;
        this.xOffset     = 0;
        this.yOffset     = 0;
    }

    /*! \fn public void centerOn(float playerX, float playerY)
        \brief Centreaza camera pe player.
               Limiteaza camera sa nu iasa din boundele hartii.
     */
    public void centerOn(float playerX, float playerY)
    {
        // centreaza camera pe player
        xOffset = playerX - wndWidth  / 2f;
        yOffset = playerY - wndHeight / 2f;

        // limiteaza camera la boundele hartii
        if(xOffset < 0)                      xOffset = 0;
        if(yOffset < 0)                      yOffset = 0;
        if(xOffset > mapWidthPx  - wndWidth)  xOffset = mapWidthPx  - wndWidth;
        if(yOffset > mapHeightPx - wndHeight) yOffset = mapHeightPx - wndHeight;
    }

    public int getXOffset() { return (int) xOffset; }
    public int getYOffset() { return (int) yOffset; }
}