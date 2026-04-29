package PaooGame;

// urmareste playerul si calculeaza offsetul de desenare
public class Camera
{
    private double xOffset;  /*!< Offsetul pe X al camerei */
    private double yOffset;  /*!< Offsetul pe Y al camerei */

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

    // centreaza pe player si clameaza la limitele hartii
    public void centerOn(double playerX, double playerY)
    {
        // centreaza camera pe player
        xOffset = playerX - wndWidth  / 2.0;
        yOffset = playerY - wndHeight / 2.0;

        // limiteaza camera la boundele hartii
        xOffset = Math.max(0, Math.min(xOffset, mapWidthPx - wndWidth));
        yOffset = Math.max(0, Math.min(yOffset, mapHeightPx - wndHeight));
    }

    public int getXOffset() {return (int) xOffset; }
    public int getYOffset() { return (int) yOffset; }
}