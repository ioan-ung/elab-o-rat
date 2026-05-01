package PaooGame;

import PaooGame.GameObjects.Entity;
import PaooGame.GameObjects.GameObject;
import PaooGame.Levels.Level;
import PaooGame.Tiles.Tile;

import java.awt.*;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;

public class CollisionChecker {
    Level level;
    public CollisionChecker (Level lvl) {
        level = lvl;
    }
    public static void checkObjects (GameObject[] objects, Entity player) {
        Rectangle objectHitbox = new Rectangle();
        Rectangle playerHitbox = new Rectangle(player.getX(),player.getY(),player.getRect().width,player.getRect().height);
        for (GameObject obj : objects) {
            objectHitbox.setBounds(obj.getX(),obj.getY(),obj.getRect().width,obj.getRect().height);
            if (playerHitbox.intersects(objectHitbox)) obj.hasCollided();
        }
    }

    public boolean checkTile (Entity entity, int xSign, int ySign) {
        Rectangle rect = entity.getRect();
        // Hitbox coordinates
        int leftX = entity.getX() + rect.x;
        int rightX = leftX + rect.width;
        int topY = entity.getY() + rect.y;
        int bottomY = topY + rect.height;

        int leftCol = leftX / TILE_ACTUAL_SIZE;
        int rightCol = rightX / TILE_ACTUAL_SIZE;
        int topRow = topY / TILE_ACTUAL_SIZE;
        int bottomRow = bottomY / TILE_ACTUAL_SIZE;

        int aTileID=0, bTileID=0;

        try {
            if (ySign < 0) { // Going Up
                topRow = (topY - entity.getSpeed()) / TILE_ACTUAL_SIZE;
                aTileID = Math.max(0, level.map.tileMapFloor[topRow][leftCol]);
                bTileID = Math.max(0, level.map.tileMapFloor[topRow][rightCol]);
            }
            if (ySign > 0) { // Going Down
                bottomRow = (bottomY + entity.getSpeed()) / TILE_ACTUAL_SIZE;
                aTileID = Math.max(0, level.map.tileMapFloor[bottomRow][leftCol]);
                bTileID = Math.max(0, level.map.tileMapFloor[bottomRow][rightCol]);
            }
            if (xSign < 0) { // Going Left
                leftCol = (leftX - entity.getSpeed()) / TILE_ACTUAL_SIZE;
                aTileID = Math.max(0, level.map.tileMapFloor[topRow][leftCol]);
                bTileID = Math.max(0, level.map.tileMapFloor[bottomRow][leftCol]);
            }
            if (xSign > 0) { // Going Right
                rightCol = (rightX + entity.getSpeed()) / TILE_ACTUAL_SIZE;
                aTileID = Math.max(0, level.map.tileMapFloor[topRow][rightCol]);
                bTileID = Math.max(0, level.map.tileMapFloor[bottomRow][rightCol]);
            }
            return Tile.tiles[aTileID].IsSolid() || Tile.tiles[bTileID].IsSolid();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Entity has gone out of bounds");
            return true;
        }
    }
}
