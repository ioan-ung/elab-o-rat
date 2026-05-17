package PaooGame.GameObjects.Entities;

import PaooGame.Algorithms.Pathfinder;
import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cat extends Entity {

    private static final int PATH_REFRESH_RATE = 30; // frames between BFS recalculations

    private int frameCounter = 0;
    private List<int[]> path = new ArrayList<>(); // [col, row] tile waypoints
    private int waypointIndex = 0;

    public Cat(int x, int y) {
        super(x, y);
        speed = 2;
        hitBox = new Rectangle(10, 10, 12, 12);
        setSprites();
    }

    @Override
    public void hasCollided() {}

    @Override
    public void update() {
        if (Level.player == null || Level.map == null) return;

        frameCounter++;
        if (frameCounter >= PATH_REFRESH_RATE || path.isEmpty() || waypointIndex >= path.size()) {
            frameCounter = 0;
            recalculatePath();
        }

        moveAlongPath();
    }

    private void recalculatePath() {
        int ts = AssetManager.TILE_ACTUAL_SIZE;
        int catCol    = (x + hitBox.x + hitBox.width  / 2) / ts;
        int catRow    = (y + hitBox.y + hitBox.height / 2) / ts;
        int playerCol = (Level.player.getX() + Level.player.getRect().x + Level.player.getRect().width  / 2) / ts;
        int playerRow = (Level.player.getY() + Level.player.getRect().y + Level.player.getRect().height / 2) / ts;

        path = Pathfinder.bfs(catCol, catRow, playerCol, playerRow);
        waypointIndex = 0;
    }

    private void moveAlongPath() {
        if (path == null || waypointIndex >= path.size()) return;

        int ts = AssetManager.TILE_ACTUAL_SIZE;
        int[] target  = path.get(waypointIndex);
        int targetX = target[0] * ts;
        int targetY = target[1] * ts;

        int dx = targetX - x;
        int dy = targetY - y;

        // Move one axis at a time to avoid cutting corners
        if (Math.abs(dx) > 0) {
            xSign = (int) Math.signum(dx);
            ySign = 0;
            x += Math.abs(dx) <= speed ? dx : xSign * speed;
        } else if (Math.abs(dy) > 0) {
            ySign = (int) Math.signum(dy);
            xSign = 0;
            y += Math.abs(dy) <= speed ? dy : ySign * speed;
        } else {
            waypointIndex++;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (baseImage != null) { super.draw(g2); return; }
        g2.setColor(new Color(255, 140, 0));
        g2.fillRect(x * AssetManager.SCALE, y * AssetManager.SCALE, AssetManager.TILE_SIZE, AssetManager.TILE_SIZE);
    }

    @Override
    protected void setSprites() {
        baseImage = AssetManager.cat;
    }
}