package PaooGame;

import PaooGame.Entity.Entity;
import PaooGame.Levels.Level;
import PaooGame.Tiles.Tile;

import java.awt.*;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;

public class CollisionChecker {
    Level level;
    public CollisionChecker (Level lvl) {
        level = lvl;
    }
    public boolean checkTile (Entity entity, int xSign, int ySign) {
        Rectangle rect = entity.getRect();
        // Hitbox coordinates
        int leftX = entity.getXPos() + rect.x;
        int rightX = leftX + rect.width;
        int topY = entity.getYPos() + rect.y;
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
