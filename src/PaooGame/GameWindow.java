package PaooGame;

import javax.swing.*;
import java.awt.*;

// fereastra principala a jocului — JFrame + Canvas pe care se deseneaza
public class GameWindow
{
    private JFrame  windowFrame;
    private String  windowTitle;
    private int     windowWidth;
    private int     windowHeight;
    private Canvas  canvas;

    public GameWindow(String title, int width, int height){
        windowTitle  = title;
        windowWidth  = width;
        windowHeight = height;
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
        windowFrame.setSize(windowWidth, windowHeight);
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        windowFrame.setLocationRelativeTo(null);
        windowFrame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(windowWidth, windowHeight));
        canvas.setMaximumSize(new Dimension(windowWidth, windowHeight));
        canvas.setMinimumSize(new Dimension(windowWidth, windowHeight));
        windowFrame.add(canvas);
        windowFrame.pack();
    }

    public int getWindowWidth()
    {
        return windowWidth;
    }

    public int getWindowHeight()
    {
        return windowHeight;
    }

    public Canvas GetCanvas() {
        return canvas;
    }
}
