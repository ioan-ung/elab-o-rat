package PaooGame.Map;

import PaooGame.GameObjects.Entities.Entity;
import PaooGame.GameObjects.GameObject;

import java.util.ArrayList;

public class GameMap {
    public int[][] tileMap;
    public int[][] tileMapAbove;
    public int mapWidth;
    public int mapHeight;
    public ArrayList<GameObject> gameObjects;
    public ArrayList<Entity> gameEntities;
}