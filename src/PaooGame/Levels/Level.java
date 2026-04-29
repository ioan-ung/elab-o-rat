package PaooGame.Levels;

import PaooGame.Camera;
import PaooGame.Entity.Mouse;
import PaooGame.GameWindow;
import PaooGame.Graphics.AssetManager;
import PaooGame.Input.KeyHandler;
import PaooGame.Map.GameMap;
import PaooGame.Map.TmxParser;
import PaooGame.Tiles.Tile;
import java.awt.*;

public abstract class Level {
    protected GameMap map;
    protected Camera camera;
    protected Mouse player;
    protected KeyHandler keyH;
    protected GameWindow gameWindow;

    public Level(GameWindow gw, KeyHandler keyH, String mapPath) {
        this.gameWindow = gw;
        this.keyH = keyH;
        gameWindow.GetCanvas().addKeyListener(keyH);
        gameWindow.GetCanvas().setFocusable(true);
        // necesar la tranzitia intre nivele — constructorul noului nivel recapata focusul pentru canvas
        gameWindow.GetCanvas().requestFocusInWindow();
        player = new Mouse(keyH);
        map = TmxParser.loadMap(mapPath);
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

    public void load() {}

    public void update() {
        player.update();
        camera.centerOn(player.getX(), player.getY());
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        if(map == null || map.tileMapFloor == null) return;
        int camX = camera.getXOffset();
        int camY = camera.getYOffset();

        drawLayer(g, map.tileMapFloor, camX, camY, windowWidth, windowHeight);

        Graphics2D g2 = (Graphics2D) g;
        g2.translate(-camX, -camY);
        player.draw(g2);
        g2.translate(camX, camY);

        drawLayer(g, map.tileMapAbove, camX, camY, windowWidth, windowHeight);
    }

    public abstract boolean isCompleted();
}