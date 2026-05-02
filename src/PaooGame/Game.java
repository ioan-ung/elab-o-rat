package PaooGame;

import PaooGame.Graphics.AssetManager;
import PaooGame.Input.KeyHandler;
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
    private KeyHandler keyH;

    public Game(String title, int width, int height)
    {
        InitGame(title, width, height);
        runState = false;
    }

    private void InitGame(String title, int width, int height)
    {
        keyH = new KeyHandler();
        window = new GameWindow(title, width, height);
        window.BuildGameWindow();
        AssetManager.Init();   // ← 1. încarcă imaginile
        Tile.Init();     // ← 2. creează tile-urile cu imaginile încărcate
        levelManager = new LevelManager(window, keyH);
        menu = new Menu(window.GetCanvas(), window.getWindowWidth(), window.getWindowHeight());
    }

    public void run() {
        long currentTime, lastTime = System.nanoTime();
        double delta = 0;   // No. of frames
        int sleepTime;

        final int FPS = 60;
        final double nsPerFrame = 1000_000_000.0 / FPS;

        while(runState) {
            // Calculate No. of frames to update
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / nsPerFrame;
            lastTime = currentTime;

            // Update the game delta times before drawing
            if (delta > 5) delta = 5;
            while(delta >= 1) {
                update(window,keyH);
                delta--;
            }
            draw();

//            // DEBUG: COMMENT THIS LATER
//            System.out.println(delta);

            // Calculate how much time is left in the current frame
            sleepTime = (int) ((nsPerFrame - (System.nanoTime() - currentTime))/1000_000);    // In milliseconds

            if (sleepTime > 0) {
                try {
                    if (sleepTime > 2) {
                        Thread.sleep(sleepTime-2);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    e.printStackTrace();
                }

                // Wait until the frame is over
                while (System.nanoTime() - currentTime < nsPerFrame) {
                    Thread.yield(); // Better than an empty loop
                }
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

    private void update(GameWindow gw, KeyHandler Kh)
    {
        if(menu.getState() == GameState.PLAYING){
            levelManager.update(gw,Kh);
        }
        if (keyH.debugOn) Debuger.reset();
    }

    private void draw()
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
            levelManager.draw(g2, window.getWindowWidth(), window.getWindowHeight());
        }

        // DEBUG_A
        if (keyH.debugOn) {
            Debuger.timeDisplay(g2,"Draw time: ",System.nanoTime()-drawStart);
            Debuger.drawText(g2,"Current Level: " + levelManager.toString());
        }
        bs.show();
        // Force the OS to synchronize the graphics pipeline
        Toolkit.getDefaultToolkit().sync();
        g2.dispose();
    }
}
