package PaooGame;

import PaooGame.Data.Database;
import PaooGame.GameObjects.Box;
import PaooGame.Input.KeyHandler;
import PaooGame.Levels.Level;
import PaooGame.Levels.LevelManager;
import PaooGame.Menus.StartMenu;
import PaooGame.Tiles.DoorTile;
import PaooGame.Tiles.Tile;

import java.awt.*;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;

public class Debuger {
    private static int noMessages = 0;
    private static int total = 100;
    private static final int fontSize = 20;
    private static final Font debugFont = new Font("SansSerif", Font.BOLD, fontSize);

    public static void reset() {
        total = noMessages;
        noMessages = 0;
    }
    public static void background (Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 300, fontSize*(total+1));
    }
    public static void timeDisplay (Graphics g, String message, long timeInterval) {
        g.setColor(Color.white);
        g.setFont(debugFont);
        g.drawString(message + timeInterval/1000_000.0 + "ms", fontSize, fontSize*++noMessages);
//        System.out.println(message + timeInterval/1000_000.0 + "ms");
    }
    public static void drawCoordinates (Graphics g, String message, int x, int y) {
        g.setColor(Color.white);
        g.setFont(debugFont);
        g.drawString(message + x +" / "+ y, fontSize, fontSize*++noMessages);
//        System.out.println(message + x +" / "+ y);
    }
    public static void drawText (Graphics g, String message) {
        g.setColor(Color.white);
        g.setFont(debugFont);
        g.drawString(message, fontSize, fontSize*++noMessages);
//        System.out.println(message);
    }

    public static void spawnBox (int x, int y) {
        if (KeyHandler.spawnBoxKey) {
            Level.map.gameObjects.add(new Box(x, y));
            Level.map.gameEntities.add(new Box(x, y));
        }
    }

    public static boolean openDoorsAround (int x, int y) {
        boolean openedDoor = false;

        if (!KeyHandler.openDoorsKey) return openedDoor;

        for (int i=x/TILE_ACTUAL_SIZE-1; i<=x/TILE_ACTUAL_SIZE+1; i++)
            for (int j=y/TILE_ACTUAL_SIZE-1; j<=y/TILE_ACTUAL_SIZE+1; j++)
                if (Tile.tiles[Level.map.tileMap[j][i]] instanceof DoorTile) {
                    openedDoor = openedDoor || LevelManager.currentLevel.openDoorAt(i,j);
                }

        return openedDoor;
    }

    public static void saveLoad () {
        if(KeyHandler.save) {
            Database.savePlayerState(LevelManager.currentLevelIndex, Level.player.getX(), Level.player.getY(), Level.player.getScore(), StartMenu.getPlayerName());
            KeyHandler.save = false;
        }
        if(KeyHandler.load) {
            Database.loadPlayerState();
            KeyHandler.load = false;
        }
    }
}
