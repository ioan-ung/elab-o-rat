package PaooGame.States;

import PaooGame.GameManager;
import PaooGame.GameManager.GameState;
import PaooGame.Graphics.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Canvas;
import java.io.File;

/*! \class Menu
    \brief Deseneaza meniul principal al jocului folosind imaginea de fundal meniu.png
           si gestioneaza interactiunile cu butoanele.
 */
public class Menu
{
    private String currentPlayerName = "";
    private GameState currentState = GameState.MENU;

    private final BufferedImage bgImage;

    private static final int SRC_W = 2604;
    private static final int SRC_H = 1600;

    /*! Coordonatele butoanelor in spatiul imaginii sursa [x, y, w, h] */
    private static final int[][] SRC_BTNS = {
        {840, 597, 411, 138},   // New Game
        {1332, 597, 411, 138},   // Continue
        {843, 792, 411, 138},   // Options
        {1320, 792, 411, 138}    // Quit Game
    };

    /*! Coordonatele leaderboard-ului in spatiul imaginii sursa */
    private static final int LB_SRC_X = 108;
    private static final int LB_SRC_Y = 1239;
    private static final int LB_SRC_W = 576;
    private static final int LB_SRC_H = 361;

    private final String[] lbNames  = {"Ioan", "Costy", "Nistor"};
    private final int[]    lbScores = {260, 180, 120};

    private int hoveredBtn = -1;
    private final Canvas canvas;
    public Menu(Canvas canvas, int wndWidth, int wndHeight)
    {
        this.canvas = canvas;
        bgImage = ImageLoader.LoadImage("/meniu.png");

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

                hoveredBtn = getHoveredButton(e.getX(), e.getY(), wndWidth, wndHeight);
                // mouse-ul are acum animatie cand ii dau hover--acea mana
                if(hoveredBtn != -1)
                    canvas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                else
                    canvas.setCursor(Cursor.getDefaultCursor());
            }
        });

    }

    public GameState getState() { return currentState; }

    private int[] scaledBtn(int idx, int wndW, int wndH)
    {
        double sx = (double) wndW / SRC_W;
        double sy = (double) wndH / SRC_H;
        int[] s = SRC_BTNS[idx];
        return new int[] {
            (int)(s[0] * sx), (int)(s[1] * sy),
            (int)(s[2] * sx), (int)(s[3] * sy)
        };
    }

    private void handleClick(int mx, int my, int wndW, int wndH)
    {
        switch(getHoveredButton(mx, my, wndW, wndH))
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

    private int getHoveredButton(int mx, int my, int wndW, int wndH)
    {
        for(int i = 0; i < SRC_BTNS.length; i++)
        {
            int[] b = scaledBtn(i, wndW, wndH);
            if(mx >= b[0] && mx <= b[0]+b[2] && my >= b[1] && my <= b[1]+b[3])
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
                int[] b = scaledBtn(i, wndWidth, wndHeight);
                g2d.setColor(new Color(255, 200, 50, 120));
                g2d.fillRoundRect(b[0], b[1], b[2], b[3], 10, 10);
                g2d.setColor(new Color(220, 130, 20, 220));
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.drawRoundRect(b[0], b[1], b[2], b[3], 10, 10);
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
