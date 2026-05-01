package PaooGame.Levels;

import PaooGame.Camera;
import PaooGame.CollisionChecker;
import PaooGame.Debuger;
import PaooGame.GameObjects.GameObject;
import PaooGame.GameObjects.Mouse;
import PaooGame.GameObjects.Spawn;
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

public abstract class Level {
    public GameMap map;
    protected Camera camera;
    protected Mouse player;
    protected KeyHandler keyH;
    protected GameWindow gameWindow;

    public Level(GameWindow gw, KeyHandler keyH, String mapPath) {
        this.gameWindow = gw;
        gameWindow.GetCanvas().requestFocusInWindow();// necesar la tranzitia intre nivele — constructorul noului nivel recapata focusul pentru canvas
        map = TmxParser.getMap(mapPath);

        initKeys(keyH);
        initPlayer();
    }

    protected void initPlayer() {
        player = new Mouse(keyH,this);
        for (int i=0; i<map.gameObjects.length; ++i) {
            if (map.gameObjects[i] instanceof Spawn) {
                player.setDefaultValues(map.gameObjects[i].getX(), map.gameObjects[i].getY());
                map.gameObjects[i] = null;
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

    protected void initKeys (KeyHandler keyH) {
        this.keyH = keyH;
        gameWindow.GetCanvas().addKeyListener(this.keyH);
        gameWindow.GetCanvas().setFocusable(true);
    }

    public void openDoorAt(int col, int row) {
        if (Tile.tiles[map.tileMap[row][col]] instanceof DoorTile){
            map.tileMap[row][col] += 6;
        } else System.out.println("This door is not closed");
    }

    public void closeDoorAt(int col, int row) {
        if (Tile.tiles[map.tileMap[row][col]] instanceof OpenDoorTile){
            map.tileMap[row][col] -= 6;
        } else System.out.println("This door is not open");
    }


    public void update() {
        player.update();
        camera.centerOn(player.getX(), player.getY());
        for (GameObject obj : map.gameObjects) {
            if (obj == null) continue;    // Skip if object's missing
            obj.update();
            CollisionChecker.checkObject(obj, player);
        }
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        if(map == null || map.tileMap == null) return;
        int camX = camera.getXOffset();
        int camY = camera.getYOffset();

        drawLayer(g, map.tileMap, camX, camY, windowWidth, windowHeight);

        Graphics2D g2 = (Graphics2D) g;

        g2.translate(-camX, -camY);
        for (GameObject obj : map.gameObjects) {
            if (obj == null) continue;    // Skip if object's missing
            obj.draw(g2);
        }
        player.draw(g2);
        g2.translate(camX, camY);

        // DEBUG
        if (keyH.debugOn) {
            Debuger.background(g);
            Debuger.drawCoordinates(g2, "X/Y: ", player.getX(), player.getY());
            Debuger.drawCoordinates(g2, "Tile: ", (player.getX() + TILE_ACTUAL_SIZE / 2) / TILE_ACTUAL_SIZE, (player.getY() + TILE_ACTUAL_SIZE / 2) / TILE_ACTUAL_SIZE);
            Debuger.drawCoordinates(g2, "Cam: ", camX, camY);
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

    public abstract boolean isCompleted();
}