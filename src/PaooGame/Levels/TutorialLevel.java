package PaooGame.Levels;

import PaooGame.CollisionChecker;
import PaooGame.GameObjects.Button;
import PaooGame.GameObjects.GameObject;
import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;
import PaooGame.Tiles.Direction;

public class TutorialLevel extends Level {

    private static final String MAP_PATH = "/maps/Tutorial.tmx";

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