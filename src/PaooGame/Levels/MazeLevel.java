package PaooGame.Levels;

import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;

public class MazeLevel extends Level {
    private static int timer = 0; // No. frames passed
    private static boolean count = false;
    private static final String MAP_PATH = "/maps/Maze.tmx";

    public MazeLevel(GameWindow gw) {
        super(gw, MAP_PATH);
    }

    @Override
    public void update() {
        super.update();
        if (count) ++timer;
    }
}
