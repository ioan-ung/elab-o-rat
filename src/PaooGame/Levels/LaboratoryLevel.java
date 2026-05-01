package PaooGame.Levels;

import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;

public class LaboratoryLevel extends Level {

    private static final String MAP_PATH = "/maps/Laboratory.tmx";

    public LaboratoryLevel(GameWindow gw, KeyHandler keyH) {
        super(gw, keyH, MAP_PATH);
    }

    @Override
    public boolean isCompleted() {
        return keyH.escapePressed;
    }
}
