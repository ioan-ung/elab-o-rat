package PaooGame;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.States.Menu;
import PaooGame.States.Menu.GameState;
import PaooGame.States.Playing;
import java.awt.*;
import java.awt.image.BufferStrategy;
import PaooGame.Tiles.Tile;

public class Game implements Runnable
{
    private GameWindow      wnd;
    private boolean         runState;
    private Thread          gameThread;
    private BufferStrategy  bs;
    private Graphics        g;
    private Menu            menu;
    private Playing         playing;

    public Game(String title, int width, int height)
    {
        InitGame(title, width, height);
        runState = false;
    }

    private void InitGame(String title, int width, int height)
    {
        wnd = new GameWindow(title, width, height);
        wnd.BuildGameWindow();
        Assets.Init();   // ← 1. încarcă imaginile
        Tile.Init();     // ← 2. creează tile-urile cu imaginile încărcate
        playing = new Playing();  // ← 3. încarcă harta
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
            playing.Update();
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
            playing.Draw(g, wnd.GetWndWidth(), wnd.GetWndHeight());
        }

        bs.show();
        g.dispose();
    }
}
