package PaooGame.Levels;

import PaooGame.GameObjects.Cheese;
import PaooGame.GameObjects.Entities.Cat;
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
    private static final String MAP_PATH = "/maps/Maze.tmx";
    private static final int CHEESE_TO_SPAWN_CAT = 3; // pisica apare dupa ce playerul ia 3 branze
    private static final int NO_CHEESE = 20; // no. cheese on the map
    private final int initialCheese;
    private static boolean catSpawned = false;
    private static int catX, catY;

    public MazeLevel(GameWindow gw) {
        super(gw, MAP_PATH);
        randomizeMapObjects();
        initialCheese = Cheese.getCheeseLeft();
    }

    // Made only for this level's cat and cheese
    private void randomizeMapObjects() {
        List<int[]> floorTiles = getFloorTiles();
        if (floorTiles.isEmpty()) return;

        Random rnd = new Random(); // RNG
        int[] pos = floorTiles.remove(rnd.nextInt(floorTiles.size()));
        // Get cat coordinates
        catX = pos[0] * TILE_ACTUAL_SIZE;
        catY = pos[1] * TILE_ACTUAL_SIZE;
        // Spawn randomized cheese
        for (int i = 0; i < NO_CHEESE; i++) {
            pos = floorTiles.remove(rnd.nextInt(floorTiles.size()));
            map.gameObjects.add(new Cheese(pos[0] * TILE_ACTUAL_SIZE, pos[1] * TILE_ACTUAL_SIZE));
        }
    }

    // Spawns cat at predefined coordinates
    private static void spawnCat() {
        Cat cat = new Cat(catX, catY);
        map.gameObjects.add(cat);
        map.gameEntities.add(cat);
        catSpawned = true;
        System.out.println("[Maze] Spawned cat at (" + catX + ", " + catY + ")");
    }

    //returneaza floorTileurile --am nevoie pt cat spawn
    private List<int[]> getFloorTiles() {
        List<int[]> tiles = new ArrayList<>();
        for (int row = 0; row < map.mapHeight; row++) {
            for (int col = 0; col < map.mapWidth; col++) {
                int idx = map.tileMap[row][col];
                if (idx < 0) continue;
                Tile t = Tile.tiles[idx];
                if (t == null || t instanceof WallTile || t instanceof DoorTile || t instanceof OpenDoorTile) continue;
                tiles.add(new int[]{col, row});
            }
        }
        return tiles;
    }

    @Override
    public void update() {
        super.update();

        //conditia de spawn la un anumit nr de cheese
        if (!catSpawned && initialCheese - Cheese.getCheeseLeft() >= CHEESE_TO_SPAWN_CAT) spawnCat();
    }
}
