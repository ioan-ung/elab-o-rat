package PaooGame.Levels;

import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;

public class LaboratoryLevel extends Level {

    private static final String MAP_PATH = "/maps/Laboratory.tmx";

    public LaboratoryLevel(GameWindow gw) {
        super(gw, MAP_PATH);
    }
}
