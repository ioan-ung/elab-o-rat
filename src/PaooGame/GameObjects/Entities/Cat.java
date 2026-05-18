package PaooGame.GameObjects.Entities;

import PaooGame.Algorithms.Pathfinder;
import PaooGame.Game;
import PaooGame.Graphics.AssetManager;
import PaooGame.Levels.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cat extends Entity {

    private static final int PATH_REFRESH_RATE = 30; // cat de des apelam bfs --30fps
    public static final int HIT_COOLDOWN = 60;      // 60 frameuri=1s --intre 2 atacuri succesive
    private static final int SCORE_PENALTY = 10;

    private int frameCounter = 0;
    private static int hitCooldown = 0;
    private List<int[]> path = new ArrayList<>(); // (col,row) --pathul piscii
    private int waypointIndex = 0;

    public Cat(int x, int y) {
        super(x, y);
        speed = 4;
        hitBox = new Rectangle(0, 0, 32, 32);
        setSprites();
    }

    @Override
    public void hasCollided() {
        if (hitCooldown > 0) return;
        Level.player.setScore(Math.max(Level.player.getScore() - SCORE_PENALTY,0));
        hitCooldown = HIT_COOLDOWN;
        Game.playSoundEfx(5);
    }

    @Override
    public void update() {
        if (Level.player == null || Level.map == null) return;

        if (hitCooldown > 0) hitCooldown--;
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
        // Placeholder when there's no image
        if (baseImage == null) {
            g2.setColor(new Color(255, 140, 0));
            g2.fillRect(x * AssetManager.SCALE, y * AssetManager.SCALE, AssetManager.TILE_SIZE, AssetManager.TILE_SIZE);
            return;
        }
        super.draw(g2);
    }

    @Override
    protected void setSprites() {
        baseImage = AssetManager.cat;
    }

    public static void resetCooldown() {
        hitCooldown = 0;
    }

    public static int getCooldown() {
        return hitCooldown;
    }
}