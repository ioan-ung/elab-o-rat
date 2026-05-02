package PaooGame;

import PaooGame.GameObjects.Box;
import PaooGame.GameObjects.Entity;
import PaooGame.GameObjects.GameObject;
import PaooGame.Levels.Level;
import PaooGame.Tiles.Tile;

import java.awt.*;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;

public class CollisionChecker {

    public static void checkObject (GameObject object, Entity entity) {
        Rectangle entityHitbox = new Rectangle(entity.getX() + entity.getRect().x,entity.getY() + entity.getRect().y,entity.getRect().width,entity.getRect().height);
        Rectangle objectHitbox = new Rectangle();
        objectHitbox.setBounds(object.getX() + object.getRect().x,object.getY() + object.getRect().y,object.getRect().width,object.getRect().height);
        // Check intersection
        if (!entityHitbox.intersects(objectHitbox)) return;

        if(object instanceof Box) object.move(entity.getXSign() * entity.getSpeed(),entity.getYSign() * entity.getSpeed());
        else object.hasCollided();
    }

    public static boolean checkTile (Entity entity, int xSign, int ySign) {
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

        // If it's direction is 0,0 then it's probably a box
        if (xSign == 0 && ySign == 0) { // Check the tiles of its corners
            return  Tile.tiles[Level.map.tileMap[topRow][rightCol]].IsBoxSolid() ||
                    Tile.tiles[Level.map.tileMap[bottomRow][rightCol]].IsBoxSolid() ||
                    Tile.tiles[Level.map.tileMap[topRow][leftCol]].IsBoxSolid() ||
                    Tile.tiles[Level.map.tileMap[bottomRow][leftCol]].IsBoxSolid();
        }

        /// TODO: If the mouse inside a tile, push it out
        int aTileID=0, bTileID=0;

        try {
            if (ySign < 0) { // Going Up
                topRow = (topY - entity.getSpeed()) / TILE_ACTUAL_SIZE;
                aTileID = Math.max(0, Level.map.tileMap[topRow][leftCol]);
                bTileID = Math.max(0, Level.map.tileMap[topRow][rightCol]);
            }
            if (ySign > 0) { // Going Down
                bottomRow = (bottomY + entity.getSpeed()) / TILE_ACTUAL_SIZE;
                aTileID = Math.max(0, Level.map.tileMap[bottomRow][leftCol]);
                bTileID = Math.max(0, Level.map.tileMap[bottomRow][rightCol]);
            }
            if (xSign < 0) { // Going Left
                leftCol = (leftX - entity.getSpeed()) / TILE_ACTUAL_SIZE;
                aTileID = Math.max(0, Level.map.tileMap[topRow][leftCol]);
                bTileID = Math.max(0, Level.map.tileMap[bottomRow][leftCol]);
            }
            if (xSign > 0) { // Going Right
                rightCol = (rightX + entity.getSpeed()) / TILE_ACTUAL_SIZE;
                aTileID = Math.max(0, Level.map.tileMap[topRow][rightCol]);
                bTileID = Math.max(0, Level.map.tileMap[bottomRow][rightCol]);
            }
            return Tile.tiles[aTileID].IsSolid() || Tile.tiles[bTileID].IsSolid();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Entity has gone out of bounds");
            return true;
        }
    }
}
