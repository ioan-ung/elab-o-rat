package PaooGame.Levels;

import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;

public class MazeLevel extends Level {

    private static final String MAP_PATH = "res/maps/Maze.tmx";

    public MazeLevel(GameWindow gw, KeyHandler keyH) {
        super(gw, keyH, MAP_PATH);
    }

    @Override
    public boolean isCompleted() {
        return keyH.escapePressed;
    }
}
