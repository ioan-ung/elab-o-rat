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

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (isFullScreen) {
            windowFrame.setUndecorated(true); // Strip borders
            gd.setFullScreenWindow(windowFrame); // Makes fullscreen

            // Gets fullscreen window dimensions
            DisplayMode mode = gd.getDisplayMode();
            currentWidth = mode.getWidth();
            currentHeight = mode.getHeight();
        } else {
            windowFrame.setUndecorated(false); // Restore borders
            gd.setFullScreenWindow(null); // Changes back from fullscreen

            // Restores normal window dimensions
            currentWidth = windowWidth;
            currentHeight = windowHeight;
            windowFrame.setSize(currentWidth, currentHeight);
            windowFrame.setLocationRelativeTo(null); // Center
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
