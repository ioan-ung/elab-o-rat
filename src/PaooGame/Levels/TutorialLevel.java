package PaooGame.Levels;

import PaooGame.GameWindow;

public class TutorialLevel extends Level {

    private static final String MAP_PATH = "/maps/Tutorial.tmx";

    public TutorialLevel(GameWindow gw) {
        super(gw, MAP_PATH);
    }
}