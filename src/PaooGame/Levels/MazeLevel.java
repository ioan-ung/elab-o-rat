package PaooGame.Levels;

import PaooGame.GameObjects.Cheese;
import PaooGame.GameWindow;
import PaooGame.Tiles.WallTile;
import PaooGame.Tiles.DoorTile;
import PaooGame.Tiles.OpenDoorTile;
import PaooGame.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;

public class MazeLevel extends Level {
    private static int timer = 0;
    private static boolean count = false;
    private static final String MAP_PATH = "/maps/Maze.tmx";

    public MazeLevel(GameWindow gw) {
        super(gw, MAP_PATH);
        randomizeCheese();
    }

    private void randomizeCheese() {
        // colecteaza pozitiile de podea valide
        List<int[]> floorTiles = new ArrayList<>();
        for (int row = 0; row < map.mapHeight; row++) {
            for (int col = 0; col < map.mapWidth; col++) {
                int idx = map.tileMap[row][col];
                if (idx < 0) continue;
                Tile t = Tile.tiles[idx];
                if (t == null) continue;
                if (t instanceof WallTile || t instanceof DoorTile || t instanceof OpenDoorTile) continue;
                floorTiles.add(new int[]{col, row});
            }
        }

        if (floorTiles.isEmpty()) return;

        Random rnd = new Random();
        int count = Math.min(10, floorTiles.size());
        for (int i = 0; i < count; i++) {
            int pick = rnd.nextInt(floorTiles.size());
            int[] pos = floorTiles.remove(pick); // scoatem tileul --sa nu fie ales din nou
            map.gameObjects.add(new Cheese(pos[0] * TILE_ACTUAL_SIZE, pos[1] * TILE_ACTUAL_SIZE));
        }
    }

    @Override
    public void update() {
        super.update();
        if (count) ++timer;
    }
}
