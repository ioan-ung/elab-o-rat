package PaooGame;

import PaooGame.Graphics.FontManager;

import javax.swing.*;
import java.awt.*;

// fereastra principala a jocului — JFrame + Canvas pe care se deseneaza
public class GameWindow
{
    private JFrame      windowFrame;
    private final String windowTitle;
    private final int   windowWidth;
    private final int   windowHeight;
    private int         currentWidth;
    private int         currentHeight;
    private Canvas      canvas;
    private boolean     isFullScreen;

    public GameWindow(String title, int width, int height){
        windowTitle  = title;
        currentWidth = windowWidth = width;
        currentHeight = windowHeight = height;
        windowFrame  = null;
    }

    // contra apelurilor multiple
    public void BuildGameWindow()
    {
        if(windowFrame != null)
        {
            return;
        }
        windowFrame = new JFrame(windowTitle);
        windowFrame.setSize(currentWidth, currentHeight);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(currentWidth, currentHeight));
        canvas.setMaximumSize(new Dimension(currentWidth, currentHeight));
        canvas.setMinimumSize(new Dimension(currentWidth, currentHeight));
        windowFrame.add(canvas);
        windowFrame.pack();
    }

    public void toggleFullScreen() {
        if (windowFrame == null) return;

        // Disposes the frame in order to change decorations
        windowFrame.dispose();
        isFullScreen = !isFullScreen;

        if (isFullScreen) {
            windowFrame.setUndecorated(true); // elimina bara de titlu si bordura
            // "windowed fullscreen": fereastra umple ecranul dar ramane in stiva normala de ferestre
            // (spre deosebire de setFullScreenWindow care da control exclusiv OS-ului si blocheaza dialogurile Swing)
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            currentWidth  = screen.width;
            currentHeight = screen.height;
            windowFrame.setSize(currentWidth, currentHeight);
            windowFrame.setLocation(0, 0); // coltul stanga-sus al ecranului
        } else {
            windowFrame.setUndecorated(false); // restaureaza bara de titlu si bordura
            currentWidth  = windowWidth;
            currentHeight = windowHeight;
            windowFrame.setSize(currentWidth, currentHeight);
            windowFrame.setLocationRelativeTo(null); // centreaza fereastra pe ecran
        }

        // Update canvas size
        canvas.setPreferredSize(new Dimension(currentWidth, currentHeight));
        canvas.setMaximumSize(new Dimension(currentWidth, currentHeight));
        canvas.setMinimumSize(new Dimension(currentWidth, currentHeight));

        // Display window
        if (!isFullScreen) windowFrame.pack(); // Only pack when returning in windowed mode
        windowFrame.setVisible(true);
        canvas.requestFocusInWindow();
    }

    public int getCurrentWidth()
    {
        return currentWidth;
    }

    public int getCurrentHeight()
    {
        return currentHeight;
    }

    public Canvas GetCanvas() {
        return canvas;
    }

    public static void drawString (Graphics2D g2, String message, int x, int y, int w, int h) {
        g2.setColor(new Color(0, 0, 100, 100));
        g2.fillRect(x, y, w, h);
        g2.setColor(Color.black);
        g2.drawRect(x, y, w, h);
        g2.setColor(Color.white);
        g2.setFont(FontManager.getFont());
        g2.drawString(message, x+10, y+20);
    }
}
