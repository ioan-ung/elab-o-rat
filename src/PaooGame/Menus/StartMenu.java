package PaooGame.Menus;

import PaooGame.Components.MenuButton;
import PaooGame.Components.PlayerNameDialog;
import PaooGame.GameManager.GameState;
import PaooGame.Graphics.ImageLoader;

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
    private String currentPlayerName = "";
    private GameState currentState = GameState.MENU;

    private final BufferedImage bgImage;

    private static final int SRC_W = 1308;
    private static final int SRC_H = 809;

    private MenuButton[] buttons;

    /*! Coordonatele butoanelor in spatiul imaginii sursa [x, y, w, h] */
    private static final int[][] SRC_BTNS = {
        {420, 300, 200, 75},   // New Game
        {663, 300, 200, 75},   // Continue
        {420, 398, 200, 75},   // Options
        {663, 398, 200, 75}    // Quit Game
    };

    /*! Coordonatele leaderboard-ului in spatiul imaginii sursa */
    private static final int LB_SRC_X = 54;
    private static final int LB_SRC_Y = 613;
    private static final int LB_SRC_W = 291;
    private static final int LB_SRC_H = 192;

    private final String[] lbNames  = {"Ioan", "Costy", "Nistor"};
    private final int[]    lbScores = {260, 180, 120};

    private int hoveredBtn = -1;
    private final Canvas canvas;
    public StartMenu(Canvas canvas, int wndWidth, int wndHeight)
    {
        this.canvas = canvas;
        bgImage = ImageLoader.LoadImage("/MenuScreen.png");

        buttons = new MenuButton[]{
                new MenuButton(SRC_BTNS[0],SRC_W, SRC_H,wndWidth,wndHeight),
                new MenuButton(SRC_BTNS[1],SRC_W, SRC_H,wndWidth,wndHeight),
                new MenuButton(SRC_BTNS[2],SRC_W, SRC_H,wndWidth,wndHeight),
                new MenuButton(SRC_BTNS[3],SRC_W, SRC_H,wndWidth,wndHeight),
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
                // mouse-ul are acum animatie cand ii dau hover--acea mana
                if(hoveredBtn != -1)
                    canvas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                else
                    canvas.setCursor(Cursor.getDefaultCursor());
            }
        });

    }

    public GameState getState() { return currentState; }


    private void handleClick(int mx, int my, int wndW, int wndH)
    {
        switch(getHoveredButton(mx, my))
        {
            case 0:
                String name = PlayerNameDialog.show(canvas);
                if(name != null)
                {
                    currentPlayerName = name;
                    currentState = GameState.PLAYING;
                    // dupa ce dialogul se inchide focusul ramane pe JFrame; il redirectam la canvas ca tastele sa ajunga la KeyManager
                    canvas.requestFocusInWindow();
                }
                break;
            case 1: currentState = GameState.PLAYING; canvas.requestFocusInWindow(); break; // idem Continue
            case 2: /* TODO: optiuni */ break;
            case 3: System.exit(0); break;
        }
    }

    private int getHoveredButton(int mx, int my)
    {
        for(int i = 0; i < SRC_BTNS.length; i++)
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
        for(int i = 0; i < SRC_BTNS.length; i++)
        {
            if(i == hoveredBtn)
            {
                buttons[i].drawHoverButton(g2d,SRC_BTNS[i]);
            }
        }

        // --- Leaderboard ---
        double sx = (double) wndWidth  / SRC_W;
        double sy = (double) wndHeight / SRC_H;
        int lbX = (int)(LB_SRC_X * sx);
        int lbY = (int)(LB_SRC_Y * sy);
        int lbW = (int)(LB_SRC_W * sx);
        int lbH = (int)(LB_SRC_H * sy);

        g2d.setColor(new Color(10, 10, 20, 200));
        g2d.fillRect(lbX, lbY, lbW, lbH);
        g2d.setColor(new Color(245, 200, 66, 200));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(lbX, lbY, lbW, lbH);


        // --- Version ---
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2d.setColor(new Color(80, 60, 40, 180));
    }
}
