package PaooGame;

import PaooGame.Data.Database;
import PaooGame.GameObjects.Cheese;
import PaooGame.Graphics.AssetManager;
import PaooGame.Graphics.FontManager;
import PaooGame.Input.Debuger;
import PaooGame.Input.KeyHandler;
import PaooGame.Levels.Level;
import PaooGame.Levels.LevelManager;
import PaooGame.Sounds.SoundPlayer;
import PaooGame.Tiles.Tile;

import PaooGame.GameManager.GameState;
import PaooGame.Menus.EndMenu;
import PaooGame.Menus.PauseMenu;
import PaooGame.Menus.StartMenu;

import java.awt.*;
import java.awt.image.BufferStrategy;

import java.util.logging.Logger;

public class Game implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    private volatile boolean runState;
    private GameWindow  window;
    private Thread      gameThread;
    private LevelManager levelManager;
    private StartMenu   startMenu;
    private PauseMenu   pauseMenu;
    private EndMenu     endMenu;
    private static SoundPlayer soundPlayer, sfxPlayer;

    public Game(String title, int width, int height)
    {
        InitGame(title, width, height);
        runState = false;
    }

    private void InitGame(String title, int width, int height)
    {
        Database.initDB();
        FontManager.init();
        window = new GameWindow(title, width, height);
        window.BuildGameWindow();
        AssetManager.Init();   // ← 1. încarcă imaginile
        Tile.Init();     // ← 2. creează tile-urile cu imaginile încărcate
        levelManager = new LevelManager(window);
        startMenu = new StartMenu(this, window.GetCanvas(), window.getCurrentWidth(), window.getCurrentHeight());
        pauseMenu = new PauseMenu();
        endMenu   = new EndMenu();
        soundPlayer = new SoundPlayer();
        sfxPlayer = new SoundPlayer();
        playSong(1);
    }

    @SuppressWarnings("BusyWait")
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
                update(window);
                delta--;
            }
            draw();

            // Calculate how much time is left in the current frame
            sleepTime = (int) ((nsPerFrame - (System.nanoTime() - currentTime))/1000_000);    // In milliseconds

            if (sleepTime > 0) {
                try {
                    if (sleepTime > 2) {
                        Thread.sleep(sleepTime-2);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    LOGGER.log(java.util.logging.Level.SEVERE, "Interrupted thread from sleep: ", e);
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
        if(!runState)
        {
            runState   = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public synchronized void StopGame()
    {
        if(runState)
        {
            runState = false;
            try {
                gameThread.join();
            }
            catch(InterruptedException e) {
                LOGGER.log(java.util.logging.Level.SEVERE, "Game thread joining was interrupted. ", e);
            }
        }
    }

    private void update(GameWindow gw)
    {
        startMenu.setCurrentState();    // Sets correct current state

        if (KeyHandler.fullScreenKey) {
            window.toggleFullScreen();
            startMenu.updateSize(window.getCurrentWidth(), window.getCurrentHeight());
            Level.updateCamera(window.getCurrentWidth(), window.getCurrentHeight());
            KeyHandler.fullScreenKey = false;
        }

        if(startMenu.getState() == GameState.PLAYING) levelManager.update(gw);

        if (KeyHandler.debugOn) {
            Debuger.reset();
            Debuger.saveLoad();
        }
    }

    private void draw()
    {
        BufferStrategy bs = window.GetCanvas().getBufferStrategy();
        if(bs == null)
        {
            try {
                window.GetCanvas().createBufferStrategy(3);
            }
            catch(Exception e) {
                LOGGER.log(java.util.logging.Level.SEVERE, "Triple buffer strategy threw an exception. ", e);
            }
            return;
        }

        Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();

        // DEBUG_A
        long drawStart = 0;
        if (KeyHandler.debugOn) {
            drawStart = System.nanoTime();
        }

        // Clear window
        g2.clearRect(0, 0, window.getCurrentWidth(), window.getCurrentHeight());
        if(startMenu.getState() == GameState.MENU) {
            // Draw main menu
            startMenu.Draw(g2, window.getCurrentWidth(), window.getCurrentHeight());
        }
        else if(startMenu.getState() == GameState.PLAYING) {
            // Draw playing area
            levelManager.draw(g2, window.getCurrentWidth(), window.getCurrentHeight());

            // Draw player score
            GameWindow.drawString(g2,"Score: " + Level.player.getScore(),window.getCurrentWidth()-120,0,120,30);
            // Draw no. cheese left only when NOT in debug mode
            if (!KeyHandler.debugOn) GameWindow.drawString(g2,"Cheese left: " + Cheese.getCheeseLeft(),0,0,180,30);
        }
        else if(startMenu.getState() == GameState.PAUSED) {
            levelManager.draw(g2, window.getCurrentWidth(), window.getCurrentHeight());
            pauseMenu.draw(g2, window.getCurrentWidth(), window.getCurrentHeight());
        }
        else if(startMenu.getState() == GameState.WON) {
            levelManager.draw(g2, window.getCurrentWidth(), window.getCurrentHeight());
            endMenu.draw(g2, window.getCurrentWidth(), window.getCurrentHeight(), Level.player.getScore());
        }

        // DEBUG_A
        if (KeyHandler.debugOn) {
            Debuger.timeDisplay(g2,"Draw time: ",System.nanoTime()-drawStart);
            Debuger.drawText(g2,"Current Level: " + levelManager.toString());
            Debuger.drawText(g2,"Player name: " + StartMenu.getPlayerName());
        }
        bs.show();
        // Force the OS to synchronize the graphics pipeline
        Toolkit.getDefaultToolkit().sync();
        g2.dispose();
    }

    public static void playSoundEfx (int i) {
        sfxPlayer.setSound(i);
        sfxPlayer.play();
    }

    public static void playSong(int songID) {
        if (soundPlayer == null) return;
            // Stops last song
        soundPlayer.stop();

        // New song ID
        if (songID <= 0) return;
        soundPlayer.setSound(-songID);
        soundPlayer.play();
        soundPlayer.loop();
    }
}
