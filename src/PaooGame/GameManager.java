package PaooGame;

public class GameManager {
    public enum GameState { MENU, PLAYING, PAUSED, WON }

    //instanta unica --specifica singleton
    private static GameManager instance;
    private int score;
    private int currentLevel;

    //singleton
    private GameManager(){}
    public static GameManager getInstance(){
        if(instance==null){
            instance=new GameManager();
        }
        return instance;
    }

    public int getScore(){
        return score;
    }
    public void addScore(int points){
        score+=points;
    }
    public int getCurrentLevel(){
        return currentLevel;
    }
    public void setCurrentLevel(int level){
        currentLevel=level;
    }
}