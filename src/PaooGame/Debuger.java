package PaooGame;

import java.awt.*;

public class Debuger {
    private static int noMessages = 0;

    public static void reset() {
        noMessages = 0;
    }

    public static void timeDisplay (Graphics g, String message, long timeInterval) {
        g.setColor(Color.white);
        g.drawString(message + timeInterval/1000_000.0 + "ms", 10, 10*++noMessages);
        System.out.println(message + timeInterval/1000_000.0 + "ms");
    }
    public static void drawCoordinates (Graphics g, String message, int x, int y) {
        g.setColor(Color.white);
        g.drawString(message + "X=" + x +" / Y="+ y, 10, 10*++noMessages);
        System.out.println(message + "X=" + x +" / Y="+ y);
    }
}
