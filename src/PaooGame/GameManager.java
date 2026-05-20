package PaooGame;

import PaooGame.Data.Database;
import PaooGame.Input.KeyHandler;

public class GameManager {
    public enum GameState { MENU, PLAYING, PAUSED, WON }
    private GameState currentState = GameState.MENU;
    //instanta unica --specifica singleton
    private static GameManager instance;
    private int currentLevelIndex;
    private String playerName = "";
    private int score;

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
                Database.savePlayerScore(score);
                Game.playSong(1);
            }
            currentState = GameState.MENU;
            return;
        }
        // Toggle Pause
        currentState = KeyHandler.pauseKey ? GameState.PAUSED : GameState.PLAYING;
    }

    public int getCurrentLevelIndex() { return currentLevelIndex; }
    public void setCurrentLevelIndex(int index) { currentLevelIndex = index; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String name) { playerName = name; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public void addScore(int points) { score += points; }
    public void subtractScore(int points) { score = Math.max(score - points, 0); }
}