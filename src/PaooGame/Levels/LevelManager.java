package PaooGame.Levels;

import PaooGame.Data.Database;
import PaooGame.Game;
import PaooGame.GameObjects.*;
import PaooGame.GameObjects.Button;
import PaooGame.GameObjects.Entities.Box;
import PaooGame.GameObjects.Entities.Cat;
import PaooGame.GameObjects.Entities.NPC_Mouse;
import PaooGame.GameWindow;
import PaooGame.Input.KeyHandler;
import PaooGame.Direction;
import PaooGame.Menus.StartMenu;

import java.awt.*;
import java.util.ArrayList;

public class LevelManager {
    public static Level currentLevel;
    public static KeyHandler keyH;
    public static int currentLevelIndex;
    public static boolean gameWon = false;
    private final LevelType[] levelOrder = {
            LevelType.TUTORIAL,
            LevelType.LABORATORY,
            LevelType.MAZE
    };

    public LevelManager(GameWindow gw) {
        LevelManager.keyH = new KeyHandler();
        gw.GetCanvas().addKeyListener(LevelManager.keyH);
        gw.GetCanvas().setFocusable(true);
    }

    public Level getLevel(LevelType type,GameWindow gw) {
        switch(type) {
            case TUTORIAL:   return new TutorialLevel(gw);
            case LABORATORY:  return new LaboratoryLevel(gw);
            case MAZE:       return new MazeLevel(gw);
            default:         return null;
        }
    }
    public static GameObject createObject(String type, int x, int y, Object[] prop) {
        try {
            switch (type) {
                case "Spawn": return new Spawn(x,y);

                case "Box": return new Box(x,y);

                case "Cat": return new Cat(x,y);

                case "Cheese": return new Cheese(x,y);

                case "Mouse":
                    @SuppressWarnings("unchecked")
                    ArrayList<Integer> xList = (ArrayList<Integer>) prop[0];
                    @SuppressWarnings("unchecked")
                    ArrayList<Integer> yList = (ArrayList<Integer>) prop[1];
                    return new NPC_Mouse(x,y,getPointList(xList,yList));

                case "Button north": return new Button(x,y,Direction.NORTH,(int) prop[0],(int) prop[1]);
                case "Button east": return new Button(x,y,Direction.EAST,(int) prop[0],(int) prop[1]);
                case "Button south": return new Button(x,y,Direction.SOUTH,(int) prop[0],(int) prop[1]);
                case "Button west": return new Button(x,y,Direction.WEST,(int) prop[0],(int) prop[1]);

                case "TimerButton north": return new TimerButton(x,y,Direction.NORTH,(int) prop[0],(int) prop[1],(int) prop[2]);
                case "TimerButton east": return new TimerButton(x,y,Direction.EAST,(int) prop[0],(int) prop[1],(int) prop[2]);
                case "TimerButton south": return new TimerButton(x,y,Direction.SOUTH,(int) prop[0],(int) prop[1],(int) prop[2]);
                case "TimerButton west": return new TimerButton(x,y,Direction.WEST,(int) prop[0],(int) prop[1],(int) prop[2]);

                case "BoxButton north": return new BoxButton(x,y,Direction.NORTH,(int) prop[0],(int) prop[1]);
                case "BoxButton east": return new BoxButton(x,y,Direction.EAST,(int) prop[0],(int) prop[1]);
                case "BoxButton south": return new BoxButton(x,y,Direction.SOUTH,(int) prop[0],(int) prop[1]);
                case "BoxButton west": return new BoxButton(x,y,Direction.WEST,(int) prop[0],(int) prop[1]);

                default:
                    System.out.println("Unknown object type found in map: " + type);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("TMX file missing object properties");
        }
        return null;
    }

    private static Point[] getPointList(ArrayList<Integer> X, ArrayList<Integer> Y) {
        int size = X.size();
        Point[] points = new Point[size];

        for (int i=0; i<size; ++i) points[i] = new Point(X.get(i),Y.get(i));

        return points;
    }

    public void update(GameWindow gw) {
        if(currentLevel == null) {
            LevelManager.currentLevelIndex = Database.getLevelIndex(); // Get level index from database
            currentLevel = getLevel(levelOrder[currentLevelIndex], gw);
            Database.loadPlayerState();     // Load game from database
            return;
        }

        if(!currentLevel.isCompleted()) {
            currentLevel.update();  // Update level
            return;
        }

        // If the level has been completed, go to the next one
        if(++currentLevelIndex < levelOrder.length) {
            currentLevel = getLevel(levelOrder[currentLevelIndex], gw);
            Game.playSoundEfx(4);
        }
        else {
            gameWon = true;    // Game is won when all the levels have been completed
            Game.playSong(3);   // Plays from ratsURL
        }
        // Save state to DB
        Database.savePlayerState(currentLevelIndex, Level.player.getX(), Level.player.getY(), Level.player.getScore(), StartMenu.getPlayerName());
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
