package PaooGame.GameObjects.Entities;

import PaooGame.Algorithms.CollisionChecker;

import java.awt.Point;

import static PaooGame.Graphics.AssetManager.TILE_ACTUAL_SIZE;

public class NPC_Mouse extends Mouse{
    private final Point[] path; // Array of points setting the path of the mouse
    private int index = 0;      // Index for the array of points

    public NPC_Mouse(int x, int y, Point[] points) {
        super(x, y);    // Set NPC coordinates
        path = points;  // Set path points
        speed = 4;      // Set speed to 4px/frame * SCALE
        xSign = ySign = 0;
    }

    @Override // Updates mouse position and faced direction
    public void update() {
        super.update();
        if (index >= path.length) return;

        xSign = Integer.signum(path[index].x-x/TILE_ACTUAL_SIZE);
        ySign = Integer.signum(path[index].y-y/TILE_ACTUAL_SIZE);
        if (xSign == 0 && ySign == 0) ++index;

        if (!(ySign == 0 || CollisionChecker.checkTile(this,0, ySign))) y += speed * ySign;
        if (!(xSign == 0 || CollisionChecker.checkTile(this, xSign,0))) x += speed * xSign;
    }
}
