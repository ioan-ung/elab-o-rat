package PaooGame.Levels;

import PaooGame.GameWindow;
import PaooGame.Input.KeyManager;

public class TutorialLevel extends Level {

    private static final String MAP_PATH = "maps/Tutorial.tmx";

    public TutorialLevel(GameWindow gw, KeyManager keyH) {
        super(gw, keyH, MAP_PATH);
    }

    //absolut provizoriu aici
    //logica de schimb a nivelului
    @Override
    public boolean isCompleted() {
        return keyH.escapePressed;
    }
}