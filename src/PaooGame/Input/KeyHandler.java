package PaooGame.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    private boolean upPressed, rightPressed, downPressed, leftPressed;
    // DEBUG KEY
    public static boolean debugOn = false;
    public static boolean movePlayer = false;   // Used to move the player an entire tile
    public static boolean nextLevel;            // folosit pentru tranzitia la nivelul urmator
    public static boolean spawnBoxKey;          // Used for spawning boxes when debugging
    public static boolean openDoorsKey;         // Used for opening doors when debugging
    public static boolean pauseKey;             // toggleaza pauza

    public boolean isUpPressed() {
        return upPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }
    public boolean isDownPressed() {
        return downPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // keyCode tells you which key has been pressed
        int keyCode = e.getKeyCode();

        // Set directional booleans
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            leftPressed = true;
        }


        // DEBUG KEY
        if (keyCode == KeyEvent.VK_F3) {
            debugOn = !debugOn;
        }
        if (keyCode == KeyEvent.VK_B) {
            spawnBoxKey = true;
        }
        if (keyCode == KeyEvent.VK_O) {
            openDoorsKey = true;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            movePlayer = true;
            pauseKey   = true;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            nextLevel = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // keyCode tells you which key has been pressed
        int keyCode = e.getKeyCode();

        // Set directional booleans
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            leftPressed = false;
        }

        // DEBUG
        if (keyCode == KeyEvent.VK_SPACE) {
            movePlayer = false;
            pauseKey   = false;
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            nextLevel = false;
        }
        if (keyCode == KeyEvent.VK_B) {
            spawnBoxKey = false;
        }
        if (keyCode == KeyEvent.VK_O) {
            openDoorsKey = false;
        }
    }
}
