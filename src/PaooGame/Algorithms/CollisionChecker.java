package PaooGame.Algorithms;

import PaooGame.GameObjects.*;
import PaooGame.GameObjects.Entities.Box;
import PaooGame.GameObjects.Entities.Entity;
import PaooGame.Levels.Level;
import PaooGame.Tiles.DoorTile;
import PaooGame.Tiles.Tile;

import java.awt.*;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;

public class CollisionChecker {

    public static boolean checkObject (GameObject object, Entity entity) {
        Rectangle entityHitbox = new Rectangle(entity.getX() + entity.getRect().x,entity.getY() + entity.getRect().y,entity.getRect().width,entity.getRect().height);
        Rectangle objectHitbox = new Rectangle();
        objectHitbox.setBounds(object.getX() + object.getRect().x,object.getY() + object.getRect().y,object.getRect().width,object.getRect().height);

        // Check intersection
        if (!entityHitbox.intersects(objectHitbox)) return false;

        if(object instanceof Box) object.move(object.getX() + entity.getXSign() * entity.getSpeed(),object.getY() + entity.getYSign() * entity.getSpeed());
        else object.hasCollided();

        return true;
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

        // If its direction is 0,0 then it's probably a box
        if (xSign == 0 && ySign == 0) { // Check the tiles of its corners
            return  Tile.tiles[Level.map.tileMap[topRow][rightCol]].IsBoxSolid() ||
                    Tile.tiles[Level.map.tileMap[bottomRow][rightCol]].IsBoxSolid() ||
                    Tile.tiles[Level.map.tileMap[topRow][leftCol]].IsBoxSolid() ||
                    Tile.tiles[Level.map.tileMap[bottomRow][leftCol]].IsBoxSolid();
        }

        int aTileID=0, bTileID=0;

        try {
            if (CollisionChecker.pushOutOfDoor(entity, leftCol, rightCol, topRow, bottomRow)) {
                leftX = entity.getX() + rect.x;
                rightX = leftX + rect.width;
                topY = entity.getY() + rect.y;
                bottomY = topY + rect.height;
            }

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
    // Called inside of checkTile
    private static boolean pushOutOfDoor(Entity entity, int leftCol, int rightCol, int topRow, int bottomRow) {
        int doorRow = 0;     // Find row
        int doorCol = 0;     // Find column
        boolean foundDoor = false;
        for (int row = topRow; row <= bottomRow; row++) {
            for (int col = leftCol; col <= rightCol; col++) {
                if (Tile.tiles[Level.map.tileMap[row][col]] instanceof DoorTile) {
                    doorCol = col;
                    doorRow = row;
                    foundDoor = true;
                }
            }
        }
        if (!foundDoor) return false; // Return if there was no door

        // 2. Calculate the exact pixel boundaries of the Entity
        Rectangle rect = entity.getRect();
        int entityLeft = entity.getX() + rect.x;
        int entityRight = entityLeft + rect.width;
        int entityTop = entity.getY() + rect.y;
        int entityBottom = entityTop + rect.height;

        // 3. Calculate the exact pixel boundaries of the Door
        int tileLeft = doorCol * TILE_ACTUAL_SIZE;
        int tileRight = tileLeft + TILE_ACTUAL_SIZE;
        int tileTop = doorRow * TILE_ACTUAL_SIZE;
        int tileBottom = tileTop + TILE_ACTUAL_SIZE;

        // 4. Calculate how deep the entity is inside the door from all 4 sides
        int pushLeft = entityRight - tileLeft;
        int pushRight = tileRight - entityLeft;
        int pushUp = entityBottom - tileTop;
        int pushDown = tileBottom - entityTop;

        // 5. Push the entity out based on the door's orientation
        if (Tile.tiles[Level.map.tileMap[doorRow][doorCol]].isOnXAxis()) {
            if (pushUp < pushDown) {
                entity.move(entity.getX(), entity.getY()-pushUp);
            } else {
                entity.move(entity.getX(), entity.getY()+pushDown);
            }
        } else {
            if (pushLeft < pushRight) {
                entity.move(entity.getX()-pushLeft, entity.getY());
            } else {
                entity.move(entity.getX()+pushRight, entity.getY());
            }
        }

        return true; // We moved the entity!
    }
}
