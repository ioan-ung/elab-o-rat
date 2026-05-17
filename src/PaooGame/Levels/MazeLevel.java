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
    private int initialCheese;
    private boolean catSpawned = false;

    public MazeLevel(GameWindow gw) {
        super(gw, MAP_PATH);
        randomizeCheese();
        initialCheese = Cheese.getCheeseLeft();
        spawnCat();
    }

    private void randomizeCheese() {
        map.gameObjects.removeIf(obj -> obj instanceof Cheese);
        Cheese.resetCheese();

        List<int[]> floorTiles = getFloorTiles();
        if (floorTiles.isEmpty()) return;

        Random rnd = new Random();
        int count = Math.min(10, floorTiles.size());
        for (int i = 0; i < count; i++) {
            int[] pos = floorTiles.remove(rnd.nextInt(floorTiles.size()));
            map.gameObjects.add(new Cheese(pos[0] * TILE_ACTUAL_SIZE, pos[1] * TILE_ACTUAL_SIZE));
        }
    }

    private void spawnCat() {
        int[] pos = new int[]{10, 10};//coordonate demo momentan


        Cat cat = new Cat(pos[0] * TILE_ACTUAL_SIZE, pos[1] * TILE_ACTUAL_SIZE);
        map.gameObjects.add(cat);
        map.gameEntities.add(cat);
        catSpawned = true;
        System.out.println("[Cat] spawnat la tile (" + pos[0] + ", " + pos[1] + ")");
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
        if (!catSpawned && initialCheese - Cheese.getCheeseLeft() >= CHEESE_TO_SPAWN_CAT)
            spawnCat();
    }
}
