package PaooGame;

import PaooGame.Data.Database;
import PaooGame.Input.KeyHandler;
import PaooGame.Levels.Level;
import PaooGame.Levels.LevelManager;

public class GameManager {
    public enum GameState { MENU, PLAYING, PAUSED, WON }
    private GameState currentState = GameState.MENU;
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

    public GameState getState() { return currentState; }
    public void setState(GameState state) { currentState = state; }

    public void updateGameState() {
        // Reset pause when in Main Menu
        if (currentState == GameState.MENU) {
            KeyHandler.pauseKey = false;
            return;
        }
        // Go back to Main Menu
        if (KeyHandler.enterKey && (currentState == GameState.PAUSED || currentState == GameState.WON)) {
            if (currentState == GameState.WON) {
                Database.savePlayerScore(Level.player.getScore());
                LevelManager.gameWon = false;
                Game.playSong(1);
            }
            currentState = GameState.MENU;
            return;
        } else if (LevelManager.gameWon) {
            currentState = GameState.WON;
            return;
        }
        // Toggle Pause
        currentState = KeyHandler.pauseKey ? GameState.PAUSED : GameState.PLAYING;
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