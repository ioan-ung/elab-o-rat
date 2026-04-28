package PaooGame.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

/*! \class public class SpriteSheetCutter
    \brief Class handles the scaling and cropping of the sprite sheet
 */
public class SpriteSheetCutter {
    private static SpriteSheet sheet;
    private static int tileSize;

    public static void useSheet(SpriteSheet sh, int tSize) {
        sheet = sh;
        tileSize = tSize;
    }

    public static BufferedImage cropAndScale(int col, int row) {
        // Create buffered image with
        BufferedImage scaled = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);

        // Draw on buffered image
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(sheet.crop(col, row), 0, 0, tileSize, tileSize, null);

        g2.dispose();

        return scaled;
    }
}
