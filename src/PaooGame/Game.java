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
    private GameWindow      window;
    private boolean         runState;
    private Thread          gameThread;
    private Menu            menu;
    private Playing         playing;

    public Game(String title, int width, int height)
    {
        InitGame(title, width, height);
        runState = false;
    }

    private void InitGame(String title, int width, int height)
    {
        window = new GameWindow(title, width, height);
        window.BuildGameWindow();
        Assets.Init();   // ← 1. încarcă imaginile
        Tile.Init();     // ← 2. creează tile-urile cu imaginile încărcate
        playing = new Playing(window);
        menu = new Menu(window.GetCanvas(), window.getWindowWidth(), window.getWindowHeight());
    }

//    public void run()
//    {
//        long oldTime = System.nanoTime();
//        long curentTime;
//
//        final int    framesPerSecond = 60;
//        final double timeFrame       = 1000000000.0 / framesPerSecond;
//
//        while(runState)
//        {
//            curentTime = System.nanoTime();
//            if((curentTime - oldTime) > timeFrame)
//            {
//                Update();
//                Draw();
//                oldTime = curentTime;
//            }
//        }
//    }
    /// Testing different run methods to understand where the lag comes from
    public void run() {
        long lastTime = System.nanoTime();
        long now;
        double delta = 0;

        final int framesPerSecond = 60;
        final double timePerTick = 1000000000.0 / framesPerSecond;

        while(runState) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            // 🚨 THE SAFETY VALVE: Prevent the Death Spiral
            // If the game lags so hard it falls 5 frames behind, cap it.
            if (delta > 5) {
                delta = 5;
            }

            while(delta >= 1) {
                Update();
                delta--;
            }

            Draw();

            // 🚨 CPU BREATHER: Give the CPU 1 millisecond to rest
            // If you don't do this, the while loop runs millions of times a second and fries your core.
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        BufferStrategy bs = window.GetCanvas().getBufferStrategy();
        if(bs == null)
        {
            try {
                window.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
        g2.clearRect(0, 0, window.getWindowWidth(), window.getWindowHeight());
        if(menu.getState() == GameState.MENU)
        {
            menu.Draw(g2, window.getWindowWidth(), window.getWindowHeight());
        }
        else if(menu.getState() == GameState.PLAYING)
        {
            playing.Draw(g2, window.getWindowWidth(), window.getWindowHeight());
        }

        bs.show();
        g2.dispose();
    }
}
