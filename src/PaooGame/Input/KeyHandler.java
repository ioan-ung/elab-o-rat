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
    public static boolean save, load;           // Used for save/load during Debug mode
    public static boolean pauseKey;             // Toggles the pause menu
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

        if (keyCode == KeyEvent.VK_ESCAPE) pauseKey = true;


        // DEBUG KEY
        if (keyCode == KeyEvent.VK_F3) {
            debugOn = !debugOn;
        }
        if (!debugOn) return;

        if (keyCode == KeyEvent.VK_B) {
            spawnBoxKey = true;
        }
        if (keyCode == KeyEvent.VK_O) {
            openDoorsKey = true;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            movePlayer = true;
        }
        if (keyCode == KeyEvent.VK_F1) {
            nextLevel = true;
        }
        if (keyCode == KeyEvent.VK_F6) {
            save = !save;
        }
        if (keyCode == KeyEvent.VK_F7) {
            load = !load;
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

        if (keyCode == KeyEvent.VK_ESCAPE) pauseKey = false;

        // DEBUG
        if (keyCode == KeyEvent.VK_SPACE) {
            movePlayer = false;
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
