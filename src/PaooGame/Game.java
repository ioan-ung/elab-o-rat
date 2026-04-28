package PaooGame;

import PaooGame.Graphics.AssetManager;
import PaooGame.Input.KeyManager;
import PaooGame.Levels.LevelManager;

import PaooGame.GameManager.GameState;
import PaooGame.States.Menu;
import java.awt.*;
import java.awt.image.BufferStrategy;
import PaooGame.Tiles.Tile;

public class Game implements Runnable
{
    private GameWindow      window;
    private boolean         runState;
    private Thread          gameThread;
    private Menu            menu;
    private LevelManager levelManager;
    private KeyManager keyH;

    public Game(String title, int width, int height)
    {
        InitGame(title, width, height);
        runState = false;
    }

    private void InitGame(String title, int width, int height)
    {
        keyH = new KeyManager();
        window = new GameWindow(title, width, height);
        window.BuildGameWindow();
        AssetManager.Init();   // ← 1. încarcă imaginile
        Tile.Init();     // ← 2. creează tile-urile cu imaginile încărcate
        levelManager = new LevelManager(window, keyH);
        menu = new Menu(window.GetCanvas(), window.getWindowWidth(), window.getWindowHeight());
    }

    public void run() {
        long lastTime = System.nanoTime();
        long currentTime;
        double delta = 0;   // No. of frames

        final int FPS = 60;
        final double timePerTick = 1000000000.0 / FPS;

        while(runState) {
            // Calculate No. of frames to update
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / timePerTick;
            lastTime = currentTime;

            // Update the game delta times before drawing
            if (delta > 5) delta = 5;
            while(delta >= 1) {
                Update(window,keyH);
                delta--;
            }
            Draw();

            // Put thread to sleeep for 1ms
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

    private void Update(GameWindow gw,KeyManager Kh)
    {
        if(menu.getState() == GameState.PLAYING)
        {
            levelManager.Update(gw,Kh);
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

        // DEBUG_A
        long drawStart = 0;
        if (keyH.debugOn) {
            drawStart = System.nanoTime();
        }

        // Clear window
        g2.clearRect(0, 0, window.getWindowWidth(), window.getWindowHeight());
        if(menu.getState() == GameState.MENU) {
            // Draw main menu
            menu.Draw(g2, window.getWindowWidth(), window.getWindowHeight());
        }
        else if(menu.getState() == GameState.PLAYING) {
            // Draw playing area
            levelManager.Draw(g2, window.getWindowWidth(), window.getWindowHeight());
        }

        // DEBUG_A
        if (keyH.debugOn) {
            long drawEnd = System.nanoTime();
            long drawTime = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + drawTime, 10, 10);
            System.out.println("Draw time: " + drawTime);
        }
        bs.show();

        g2.dispose();
    }
}
