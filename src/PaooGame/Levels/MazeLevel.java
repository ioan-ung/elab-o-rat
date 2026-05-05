package PaooGame.Levels;

import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;

public class MazeLevel extends Level {

    private static final String MAP_PATH = "/maps/Maze.tmx";

    public MazeLevel(GameWindow gw) {
        super(gw, MAP_PATH);
    }
}
