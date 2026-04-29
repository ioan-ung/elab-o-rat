package PaooGame.Levels;

import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;

public class TutorialLevel extends Level {

    private static final String MAP_PATH = "res/maps/Tutorial.tmx";

    public TutorialLevel(GameWindow gw, KeyHandler keyH) {
        super(gw, keyH, MAP_PATH);
    }

    //absolut provizoriu aici
    //logica de schimb a nivelului
    @Override
    public boolean isCompleted() {
        return keyH.escapePressed;
    }
}