package PaooGame.Graphics;

import java.awt.Font;
import java.io.InputStream;

import java.awt.FontFormatException;
import java.io.IOException;

public class FontManager {
    private static Font cheeseFont;
    private static final int fontSize = 20;

    public static void init() {
        try (InputStream is = FontManager.class.getResourceAsStream("/FreeCheese_Font_1_0/TrueType (.ttf)/FreeCheese-Regular.ttf")) {
            if (is == null) throw new NullPointerException("Font file not found. Loading Arial font.");

            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);

            cheeseFont = baseFont.deriveFont(Font.PLAIN, fontSize);

            System.out.println("[FontManager] Cheese Font loaded successfully!");

        } catch (NullPointerException | IOException | FontFormatException e) {
            System.err.println("Failed to load custom font. Loading Arial font.");
            e.printStackTrace();
            cheeseFont = new Font("Arial", Font.BOLD, fontSize); // Fallback
        }
    }

    public static Font getFont () {
        return cheeseFont;
    }
}
