package PaooGame.Levels;

import PaooGame.CollisionChecker;
import PaooGame.GameObjects.Button;
import PaooGame.GameObjects.GameObject;
import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;
import PaooGame.Tiles.Direction;

public class TutorialLevel extends Level {

    private static final String MAP_PATH = "res/maps/Tutorial.tmx";

    public TutorialLevel(GameWindow gw, KeyHandler keyH) {
        super(gw, keyH, MAP_PATH);
        player.setDefaultValues(64,480);
        gameObjects = new GameObject[1];
        gameObjects[0] = new Button(4,14, Direction.NORTH);
    }

    //absolut provizoriu aici
    //logica de schimb a nivelului
    @Override
    public boolean isCompleted() {
        return keyH.escapePressed;
    }
}