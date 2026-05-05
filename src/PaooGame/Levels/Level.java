package PaooGame.Levels;

import PaooGame.Camera;
import PaooGame.CollisionChecker;
import PaooGame.Debuger;
import PaooGame.GameObjects.*;
import PaooGame.GameWindow;
import PaooGame.Graphics.AssetManager;
import PaooGame.Input.KeyHandler;
import PaooGame.Map.GameMap;
import PaooGame.Map.TmxParser;
import PaooGame.Tiles.DoorTile;
import PaooGame.Tiles.OpenDoorTile;
import PaooGame.Tiles.Tile;
import java.awt.*;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;
import static PaooGame.Graphics.AssetManager.box;

public abstract class Level {
    public static GameMap map;
    protected Camera camera;
    protected Player player;
    protected GameWindow gameWindow;

    public Level(GameWindow gw, String mapPath) {
        this.gameWindow = gw;
        gameWindow.GetCanvas().requestFocusInWindow();// necesar la tranzitia intre nivele — constructorul noului nivel recapata focusul pentru canvas
        map = TmxParser.getMap(mapPath);

        initPlayer();
    }

    protected void initPlayer() {
        player = new Player(LevelManager.keyH);
        for (GameObject obj : map.gameObjects) {
            if (obj instanceof Spawn) {
                player.setDefaultValues(obj.getX(), obj.getY());
                map.gameObjects.remove(obj);
                break;
            }
        }
        initCamera();
    }

    protected void initCamera() {
        camera = new Camera(
                gameWindow.getWindowWidth(), gameWindow.getWindowHeight(),
                map.mapWidth  * AssetManager.TILE_SIZE,
                map.mapHeight * AssetManager.TILE_SIZE
        );
        camera.centerOn(player.getX(), player.getY());
    }

    public boolean openDoorAt(int col, int row) {
        boolean ret = Tile.tiles[map.tileMap[row][col]] instanceof DoorTile;
        if (ret) map.tileMap[row][col] += 6;
        else System.out.println("This door is not closed");
        return ret;
    }

    public boolean closeDoorAt(int col, int row) {
        boolean ret = Tile.tiles[map.tileMap[row][col]] instanceof OpenDoorTile;
        if (ret) map.tileMap[row][col] -= 6;
        else System.out.println("This door is not open");
        return ret;
    }


    public void update() {
        player.update();
        camera.centerOn(player.getX(), player.getY());

        // DEBUG:
        if (KeyHandler.debugOn) {
            Debuger.spawnBox(player.getX(), player.getY() + TILE_ACTUAL_SIZE);
            Debuger.openDoorsAround(player.getX(), player.getY());
        }

        for (GameObject obj : map.gameObjects) {
            if (obj == null) continue;    // Skip if object's missing
            if (obj instanceof BoxButton) {
                for (Entity entity : map.gameEntities) {
                    if (entity instanceof Box) CollisionChecker.checkObject(obj, entity);
                }
            }
            else CollisionChecker.checkObject(obj, player);
            obj.update();
        }
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        if(map == null || map.tileMap == null) return;
        int camX = camera.getXOffset();
        int camY = camera.getYOffset();

        drawLayer(g, map.tileMap, camX, camY, windowWidth, windowHeight);

        Graphics2D g2 = (Graphics2D) g;

        g2.translate(-camX, -camY);
        // First draw non-entity game objects
        for (GameObject obj : map.gameObjects) {
            if (obj == null || obj instanceof Entity) continue;    // Skip if object's missing or an entity
            obj.draw(g2);
        }
        // Then draw entities
        for (GameObject obj : map.gameEntities) {
            if (obj == null) continue;    // Skip if object's missing
            obj.draw(g2);
        }
        player.draw(g2);
        g2.translate(camX, camY);

        // DEBUG
        if (KeyHandler.debugOn) {
            Debuger.background(g);
            Debuger.drawCoordinates(g2, "X/Y: ", player.getX(), player.getY());
            Debuger.drawCoordinates(g2, "Tile: ", (player.getX() + TILE_ACTUAL_SIZE / 2) / TILE_ACTUAL_SIZE, (player.getY() + TILE_ACTUAL_SIZE / 2) / TILE_ACTUAL_SIZE);
            Debuger.drawCoordinates(g2, "Cam: ", camX, camY);
            Debuger.drawText(g2,"Cheese left: " + Cheese.getCheeseLeft());
            if (KeyHandler.movePlayer) {
                player.move(player.getXSign() * AssetManager.TILE_ACTUAL_SIZE, player.getYSign() * AssetManager.TILE_ACTUAL_SIZE);
            }
        }

//        drawLayer(g, map.tileMapAbove, camX, camY, windowWidth, windowHeight);
    }

    protected void drawLayer(Graphics g, int[][] layer, int camX, int camY, int windowWidth, int windowHeight) {
        if(map == null) return;
        int startCol = Math.max(0, camX / AssetManager.TILE_SIZE);
        int startRow = Math.max(0, camY / AssetManager.TILE_SIZE);
        int endCol   = Math.min(startCol + windowWidth  / AssetManager.TILE_SIZE + 2, map.mapWidth);
        int endRow   = Math.min(startRow + windowHeight / AssetManager.TILE_SIZE + 2, map.mapHeight);
        for(int row = startRow; row < endRow; row++)
            for(int col = startCol; col < endCol; col++) {
                int tileIdx = layer[row][col];
                if(tileIdx < 0) continue;
                Tile tile = Tile.tiles[tileIdx];
                if(tile == null) continue;
                int drawX = col * AssetManager.TILE_SIZE - camX;
                int drawY = row * AssetManager.TILE_SIZE - camY;
                tile.Draw(g, drawX, drawY);
            }
    }

    public boolean isCompleted() {
        if (KeyHandler.debugOn && KeyHandler.nextLevel) {
            KeyHandler.nextLevel = false;   // Reset the key input
            return true;
        }
        return Cheese.getCheeseLeft() == 0;
    }
}