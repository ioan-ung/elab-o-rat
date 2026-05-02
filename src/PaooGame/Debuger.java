package PaooGame;

import PaooGame.GameObjects.Entity;
import PaooGame.GameObjects.Mouse;
import PaooGame.Graphics.AssetManager;

import java.awt.*;

public class Debuger {
    private static int noMessages = 0;
    private static int total = 100;
    private static final int fontSize = 20;
    private static final Font debugFont = new Font("SansSerif", Font.BOLD, fontSize);

    public static void reset() {
        total = noMessages;
        noMessages = 0;
    }
    public static void background (Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 300, fontSize*(total+1));
    }
    public static void timeDisplay (Graphics g, String message, long timeInterval) {
        g.setColor(Color.white);
        g.setFont(debugFont);
        g.drawString(message + timeInterval/1000_000.0 + "ms", fontSize, fontSize*++noMessages);
//        System.out.println(message + timeInterval/1000_000.0 + "ms");
    }
    public static void drawCoordinates (Graphics g, String message, int x, int y) {
        g.setColor(Color.white);
        g.setFont(debugFont);
        g.drawString(message + x +" / "+ y, fontSize, fontSize*++noMessages);
//        System.out.println(message + x +" / "+ y);
    }
    public static void drawText (Graphics g, String message) {
        g.setColor(Color.white);
        g.setFont(debugFont);
        g.drawString(message, fontSize, fontSize*++noMessages);
//        System.out.println(message);
    }
}
