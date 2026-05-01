package PaooGame.Levels;

import PaooGame.GameObjects.*;
import PaooGame.GameObjects.Button;
import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;
import PaooGame.Tiles.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public static Level currentLevel;
    private List<Level> levels;
    private int currentLevelIndex = 0;
    private final LevelType[] levelOrder = {
            LevelType.TUTORIAL,
            LevelType.LABORATORY,
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
            case LABORATORY:  return new LaboratoryLevel(gw, keyH);
//            case MAZE:       return new MazeLevel(gw, keyH);
            default:         return new TutorialLevel(gw, keyH);
        }
    }
    public static GameObject createObject(String type, int x, int y, String[] prop) {
        try {
            switch (type) {
                case "Spawn": return new Spawn(x,y);
//            case "Mouse":
//                return new Mouse(x, y); // Assuming your Mouse constructor takes x and y
                case "Button north": return new Button(x,y,Direction.NORTH,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "Button east": return new Button(x,y,Direction.EAST,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "Button south": return new Button(x,y,Direction.SOUTH,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "Button west": return new Button(x,y,Direction.WEST,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                default:
                    System.out.println("Unknown object type found in map: " + type);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("TMX file missing object properties");
        }
        return null;
    }

    public void update(GameWindow gw, KeyHandler keyH) {
        if(currentLevel.isCompleted()) {
            currentLevelIndex++;
            keyH.escapePressed = false; // resetam flag-ul--nu vrem sa facem switch decat o data
            if(currentLevelIndex < levelOrder.length)
                currentLevel = getLevel(levelOrder[currentLevelIndex],gw, keyH);
        } else {
            currentLevel.update();
        }
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        if (currentLevel != null) {
            currentLevel.draw(g, windowWidth, windowHeight);
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
