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
    public static KeyHandler keyH;
    private List<Level> levels;
    private int currentLevelIndex = 0;
    private final LevelType[] levelOrder = {
            LevelType.TUTORIAL,
            LevelType.LABORATORY,
            LevelType.MAZE
    };

    public LevelManager(GameWindow gw) {
        LevelManager.keyH = new KeyHandler();
        gw.GetCanvas().addKeyListener(LevelManager.keyH);
        gw.GetCanvas().setFocusable(true);
        levels = new ArrayList<>();
        TutorialLevel tutorial = new TutorialLevel(gw);
        levels.add(tutorial);
        currentLevel = tutorial;
    }

    public Level getLevel(LevelType type,GameWindow gw) {
        switch(type) {
            case TUTORIAL:   return new TutorialLevel(gw);
            case LABORATORY:  return new LaboratoryLevel(gw);
            case MAZE:       return new MazeLevel(gw);
            default:         return null;
        }
    }
    public static GameObject createObject(String type, int x, int y, String[] prop) {
        try {
            switch (type) {
                case "Spawn": return new Spawn(x,y);

                case "Box": return new Box(x,y);

                case "Cheese": return new Cheese(x,y);

                case "Button north": return new Button(x,y,Direction.NORTH,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "Button east": return new Button(x,y,Direction.EAST,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "Button south": return new Button(x,y,Direction.SOUTH,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "Button west": return new Button(x,y,Direction.WEST,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));

                case "TimerButton north": return new TimerButton(x,y,Direction.NORTH,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]),Integer.parseInt(prop[2]));
                case "TimerButton east": return new TimerButton(x,y,Direction.EAST,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]),Integer.parseInt(prop[2]));
                case "TimerButton south": return new TimerButton(x,y,Direction.SOUTH,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]),Integer.parseInt(prop[2]));
                case "TimerButton west": return new TimerButton(x,y,Direction.WEST,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]),Integer.parseInt(prop[2]));

                case "BoxButton north": return new BoxButton(x,y,Direction.NORTH,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "BoxButton east": return new BoxButton(x,y,Direction.EAST,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "BoxButton south": return new BoxButton(x,y,Direction.SOUTH,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));
                case "BoxButton west": return new BoxButton(x,y,Direction.WEST,Integer.parseInt(prop[0]),Integer.parseInt(prop[1]));

                default:
                    System.out.println("Unknown object type found in map: " + type);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("TMX file missing object properties");
        }
        return null;
    }

    public void update(GameWindow gw) {
        if(!currentLevel.isCompleted()) {
            currentLevel.update();
            return;
        }
        ++currentLevelIndex;
        if(currentLevelIndex < levelOrder.length) currentLevel = getLevel(levelOrder[currentLevelIndex],gw);
        else {
            System.out.println("Game has ended");
            System.exit(0);
        }
    }

    public void draw(Graphics g, int windowWidth, int windowHeight) {
        if (currentLevel != null) {
            currentLevel.draw(g, windowWidth, windowHeight);
        }
    }

    @Override
    public String toString() {
        switch (currentLevelIndex) {
            case 0: return "Tutorial";
            case 1: return "Laboratory";
            case 2: return "Maze";
            default: return "Unknown";
        }
    }
}
