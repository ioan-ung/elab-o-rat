package PaooGame.Levels;

import PaooGame.GameWindow;
import PaooGame.Input.KeyManager;

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

    public LevelManager(GameWindow gw, KeyManager keyH) {
        levels = new ArrayList<>();
        TutorialLevel tutorial = new TutorialLevel(gw, keyH);
        levels.add(tutorial);
        currentLevel = tutorial;
    }

    public Level getLevel(LevelType type,GameWindow gw, KeyManager keyH) {
        switch(type) {
            case TUTORIAL:   return new TutorialLevel(gw, keyH);
            case LABYRINTH:  return new LaboratoryLevel(gw, keyH);
//            case MAZE:       return new MazeLevel(gw, keyH);
            default:         return new TutorialLevel(gw, keyH);
        }
    }

    public void Update(GameWindow gw, KeyManager keyH) {
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
