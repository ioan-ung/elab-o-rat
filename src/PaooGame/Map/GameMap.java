package PaooGame.Map;

import PaooGame.GameObjects.Entity;
import PaooGame.GameObjects.GameObject;

public class GameMap {
    public int[][] tileMap;
    public int[][] tileMapAbove;
    public int mapWidth;
    public int mapHeight;
    public GameObject[] gameObjects;
    public Entity[] gameEntities;
}