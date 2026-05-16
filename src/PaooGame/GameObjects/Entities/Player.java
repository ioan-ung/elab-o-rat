package PaooGame.GameObjects.Entities;

import PaooGame.CollisionChecker;
import PaooGame.Input.KeyHandler;

public class Player extends Mouse{
    protected final KeyHandler keyH;    // Handles keyboard input
    private int score = 0;              // Score that eventually gets put into the Leaderboard

    // Constructor of Player
    public Player(int x, int y, KeyHandler keyH) {
        super(x,y);
        this.keyH = keyH;
        speed = 4;
        xSign = ySign = 0;
    }

    @Override // Updates player position and faced direction
    public void update() {
        super.update();
        // First, set direction of movement
        xSign = ySign = 0;
        if (keyH.isUpPressed())    ySign -= 1;
        if (keyH.isDownPressed())  ySign += 1;
        if (keyH.isLeftPressed())  xSign -= 1;
        if (keyH.isRightPressed()) xSign += 1;
        // Second, change position based on speed and collision
        if (!(ySign == 0 || CollisionChecker.checkTile(this,0, ySign))) y += speed * ySign;
        if (!(xSign == 0 || CollisionChecker.checkTile(this, xSign,0))) x += speed * xSign;
    }

    public void setScore (int score) {
        this.score = score;
    }
    public int getScore () {
        return score;
    }
}
