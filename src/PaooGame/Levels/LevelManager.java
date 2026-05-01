package PaooGame.Levels;

import PaooGame.Debuger;
import PaooGame.GameObjects.GameObject;
import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private Level currentLevel;
    private List<Level> levels;
    private int currentLevelIndex = 0;
    private final LevelType[] levelOrder = {
            LevelType.TUTORIAL,
            LevelType.LABYRINTH,
            LevelType.MAZE
    };

    public LevelManager(GameWindow gw, KeyHandler keyH) {
        levels = new ArrayList<>();
        TutorialLevel tutorial = new TutorialLevel(gw, keyH);
        levels.add(tutorial);
        currentLevel = tutorial;
    }

    public Level getLevel(LevelType type,GameWindow gw, KeyHandler keyH) {
        switch(type) {
            case TUTORIAL:   return new TutorialLevel(gw, keyH);
            case LABYRINTH:  return new LaboratoryLevel(gw, keyH);
//            case MAZE:       return new MazeLevel(gw, keyH);
            default:         return new TutorialLevel(gw, keyH);
        }
    }
    public static GameObject createObject(String type, float x, float y) {
        switch (type) {
//            case "Mouse":
//                return new Mouse(x, y); // Assuming your Mouse constructor takes x and y
//            case "Button":
//                return new Button(x, y);
            default:
                System.out.println("Unknown object type found in map: " + type);
                return null;
        }
    }

    public void Update(GameWindow gw, KeyHandler keyH) {
        if(currentLevel.isCompleted()) {
            currentLevelIndex++;
            keyH.escapePressed = false; // resetam flag-ul--nu vrem sa facem switch decat o data
            if(currentLevelIndex < levelOrder.length)
                currentLevel = getLevel(levelOrder[currentLevelIndex],gw, keyH);
        } else {
            currentLevel.update();
        }
    }

    public void Draw(Graphics g, int windowWidth, int windowHeight) {
        if (currentLevel != null) {
            currentLevel.draw(g, windowWidth, windowHeight);
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
