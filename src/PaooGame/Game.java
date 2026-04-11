package PaooGame;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.States.Menu;
import PaooGame.States.Menu.GameState;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable
{
    private GameWindow      wnd;
    private boolean         runState;
    private Thread          gameThread;
    private BufferStrategy  bs;
    private Graphics        g;
    private Menu            menu;

    public Game(String title, int width, int height)
    {
        InitGame(title, width, height);
        runState = false;
    }

    private void InitGame(String title, int width, int height)
    {
        wnd = new GameWindow(title, width, height);
        wnd.BuildGameWindow();
        Assets.Init();
        menu = new Menu(wnd.GetCanvas(), wnd.GetWndWidth(), wnd.GetWndHeight());
    }

    public void run()
    {
        long oldTime = System.nanoTime();
        long curentTime;

        final int    framesPerSecond = 60;
        final double timeFrame       = 1000000000.0 / framesPerSecond;

        while(runState == true)
        {
            curentTime = System.nanoTime();
            if((curentTime - oldTime) > timeFrame)
            {
                Update();
                Draw();
                oldTime = curentTime;
            }
        }
    }

    public synchronized void StartGame()
    {
        if(runState == false)
        {
            runState   = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public synchronized void StopGame()
    {
        if(runState == true)
        {
            runState = false;
            try { gameThread.join(); }
            catch(InterruptedException ex) { ex.printStackTrace(); }
        }
    }

    private void Update()
    {
        if(menu.getState() == GameState.PLAYING)
        {
            // TODO: update logica jocului
        }
    }

    private void Draw()
    {
        bs = wnd.GetCanvas().getBufferStrategy();
        if(bs == null)
        {
            try { wnd.GetCanvas().createBufferStrategy(3); return; }
            catch(Exception e) { e.printStackTrace(); }
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        if(menu.getState() == GameState.MENU)
        {
            menu.Draw(g, wnd.GetWndWidth(), wnd.GetWndHeight());
        }
        else if(menu.getState() == GameState.PLAYING)
        {
            // TODO: deseneaza harta, player etc.
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
            g.setColor(Color.CYAN);
            g.setFont(new Font("Monospaced", Font.BOLD, 24));
            g.drawString("Game started!", 300, 300);
        }

        bs.show();
        g.dispose();
    }
}
