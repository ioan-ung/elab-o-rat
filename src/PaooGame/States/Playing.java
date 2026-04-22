package PaooGame.States;

import PaooGame.Camera;
import PaooGame.Entity.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Input.KeyHandler;
import PaooGame.Map.GameMap;
import PaooGame.Tiles.Tile;
import java.awt.*;

public class Playing
{
    private GameMap map;
    private Camera camera;

    private KeyHandler keyH;
    private Player player;

    public Playing(GameWindow gameWindow, KeyHandler keyH, GameMap map)
    {
        this.map=map;
        /// Initialize player
        this.keyH = keyH;
        gameWindow.GetCanvas().addKeyListener(keyH);
        gameWindow.GetCanvas().setFocusable(true);
        player = new Player(keyH);

        //TmxParser.loadMap(map);
        /// Camera work
        camera = new Camera(
                gameWindow.getWindowWidth(), gameWindow.getWindowHeight(),
                map.mapWidth  * Assets.TILE_SIZE,
                map.mapHeight * Assets.TILE_SIZE
        );
        camera.centerOn(player.getX(), player.getY());
    }


    private void drawLayer(Graphics g, int[][] layer, int camX, int camY, int windowWidth, int windowHeight)
    {
        if(map == null) return;

        int startCol = Math.max(0, camX / Assets.TILE_SIZE);
        int startRow = Math.max(0, camY / Assets.TILE_SIZE);
        int endCol   = Math.min(startCol + windowWidth  / Assets.TILE_SIZE + 2, map.mapWidth);
        int endRow   = Math.min(startRow + windowHeight / Assets.TILE_SIZE + 2, map.mapHeight);

        for(int row = startRow; row < endRow; row++)
        {
            for(int col = startCol; col < endCol; col++)
            {
                int tileIdx = layer[row][col];
                if(tileIdx < 0) continue;

                Tile tile = Tile.tiles[tileIdx];
                if(tile == null) continue;

                int drawX = col * Assets.TILE_SIZE - camX;
                int drawY = row * Assets.TILE_SIZE - camY;

                tile.Draw(g, drawX, drawY);
            }
        }
    }


    public void Draw(Graphics g, int windowWidth, int windowHeight)
    {
        if(map.tileMapFloor == null) return;

        int camX = camera.getXOffset();
        int camY = camera.getYOffset();

        // 1. Desenam floor + background
        drawLayer(g, map.tileMapFloor, camX, camY, windowWidth, windowHeight);

        // 2. Desenam playerul aici
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(-camX, -camY);
        player.draw(g2);
        g2.translate(camX, camY);

        // 3. Desenam obiectele de deasupra (branza etc.)
        drawLayer(g, map.tileMapAbove, camX, camY, windowWidth, windowHeight);
    }


    public void Update()
    {
        player.update();
        camera.centerOn(player.getX(), player.getY());
    }
}