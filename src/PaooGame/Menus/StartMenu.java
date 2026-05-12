package PaooGame.Menus;

import PaooGame.Data.Database;
import PaooGame.GameManager;
import PaooGame.Components.Leaderboard;
import PaooGame.Components.MenuButton;
import PaooGame.Components.MenuConfig;
import PaooGame.Components.PlayerNameDialog;
import PaooGame.GameManager.GameState;
import PaooGame.Graphics.AssetManager;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Input.KeyHandler;
import PaooGame.Levels.Level;
import PaooGame.Levels.LevelManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Canvas;

/*! \class Menu
    \brief Deseneaza meniul principal al jocului folosind imaginea de fundal meniu.png
           si gestioneaza interactiunile cu butoanele.
 */
public class StartMenu
{
    private static String currentPlayerName = "";
    private GameState currentState = GameState.MENU;

    private final BufferedImage bgImage;

    private MenuButton[] buttons;

    /*! Coordonatele butoanelor in spatiul imaginii sursa [x, y, w, h] */

    private final Leaderboard leaderboard = new Leaderboard(
        new String[]{"Ioan", "Costy", "Nistor"},
        new int[]   {260, 180, 120}
    );

    private int hoveredBtn = -1;
    private final Canvas canvas;
    public StartMenu(Canvas canvas, int wndWidth, int wndHeight)
    {
        this.canvas = canvas;
        bgImage = ImageLoader.LoadImage("/MenuScreen.jpeg");

        buttons = new MenuButton[]{
                new MenuButton(MenuConfig.SRC_BTNS[0], MenuConfig.buttonNames[0], AssetManager.mouseEast,  wndWidth, wndHeight),
                new MenuButton(MenuConfig.SRC_BTNS[1], MenuConfig.buttonNames[1], AssetManager.mouseWest, wndWidth, wndHeight),
                new MenuButton(MenuConfig.SRC_BTNS[2], MenuConfig.buttonNames[2], AssetManager.mouseEast,  wndWidth, wndHeight),
                new MenuButton(MenuConfig.SRC_BTNS[3], MenuConfig.buttonNames[3], AssetManager.mouseWest, wndWidth, wndHeight),
        };

        canvas.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(currentState != GameState.MENU) return;
                handleClick(e.getX(), e.getY(), wndWidth, wndHeight);
            }
        });

        canvas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                if(currentState != GameState.MENU) return;

                hoveredBtn = getHoveredButton(e.getX(), e.getY());
                if(hoveredBtn != -1)
                    canvas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                else
                    canvas.setCursor(Cursor.getDefaultCursor());
            }
        });

    }

    public GameState getState() { return currentState; }

    public void setCurrentState() {
        if (currentState == GameState.MENU || currentState == GameState.WON) return;
        currentState = KeyHandler.pauseKey ? GameState.PAUSED : GameState.PLAYING;
    }

    public void setWon()  { currentState = GameState.WON; }
    public void setMenu() { currentState = GameState.MENU; }

    private void handleClick(int mx, int my, int wndW, int wndH)
    {
        switch(getHoveredButton(mx, my))
        {
            case 0:
                String name = PlayerNameDialog.show(canvas);
                if(name != null)
                {
                    currentPlayerName = name;
                    Database.startNewGame(name);    // Set database to new game
                    currentState = GameState.PLAYING;
                    // dupa ce dialogul se inchide focusul ramane pe JFrame; il redirectam la canvas ca tastele sa ajunga la KeyManager
                    canvas.requestFocusInWindow();
                }
                break;
            case 1:
                currentPlayerName = Database.getPlayerName();
                if (currentPlayerName.isEmpty()) break;
                currentState = GameState.PLAYING;
                canvas.requestFocusInWindow();
                break; // idem Continue
            case 2: /* TODO: optiuni */ break;
            case 3: System.exit(0); break;
        }
    }

    private int getHoveredButton(int mx, int my)
    {
        for(int i = 0; i < MenuConfig.SRC_BTNS.length; i++)
        {
            if(buttons[i].contains(mx,my))
                return i;
        }
        return -1;
    }


    public void Draw(Graphics g, int wndWidth, int wndHeight)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // --- Fundal ---
        if(bgImage != null)
            g2d.drawImage(bgImage, 0, 0, wndWidth, wndHeight, null);
        else
        {
            g2d.setColor(new Color(245, 240, 220));
            g2d.fillRect(0, 0, wndWidth, wndHeight);
        }

        // --- Highlight hover butoane ---
        for(int i = 0; i < buttons.length; i++)
        {
            if(i == hoveredBtn)
                buttons[i].drawHoverButton(g2d);
            buttons[i].draw(g2d);
        }

        // --- Leaderboard ---
        leaderboard.draw(g2d, wndWidth, wndHeight);

        // --- Version ---
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2d.setColor(new Color(80, 60, 40, 180));
    }

    public static String getPlayerName() {
        return currentPlayerName;
    }
}
