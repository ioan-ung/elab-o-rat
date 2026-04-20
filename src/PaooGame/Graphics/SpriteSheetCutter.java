package PaooGame.Graphics;

import PaooGame.States.Playing;

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
        // 1. Decupeaza imaginea la dimensiunea ei originala (ex: 32x32)
        BufferedImage original = sheet.crop(col, row);

        // 2. Creeaza o plansa noua, goala, de dimensiunea dorita (ex: 64x64)
        BufferedImage scaled = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);

        // 3. Deseneaza imaginea originala pe plansa noua, marind-o
        Graphics2D g2 = scaled.createGraphics();

        // Asigura-te ca pixelii raman clari (fara antialiasing care face imaginea blurata)
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g2.drawImage(original, 0, 0, tileSize, tileSize, null);
        g2.dispose();

        return scaled;
    }
}
