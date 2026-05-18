package PaooGame.Components;

import java.awt.*;

public class MenuConfig {
    //leaderboard
    public static final int leaderBoard_X = 54;
    public static final int leaderBoard_Y = 613;
    public static final int leaderBoard_W = 291;
    public static final int leaderBoard_H = 192;


    //menu
    public static final int[][] SRC_BTNS = {
            {420, 300, 200, 75},   // New Game
            {663, 300, 200, 75},   // Continue
            {420, 398, 200, 75},   // Guide
            {663, 398, 200, 75}    // Quit Game
    };
    public static String[] buttonNames = {
            "New Game",
            "Continue",
            "Guide",
            "Quit Game"
    };

    public static final int startMenu_W = 1308;
    public static final int startMenu_H = 809;



    public static final Color BG_COLOR     = new Color(20, 20, 35);
    public static final Color ACCENT_COLOR = new Color(220, 180, 60);
    public static final Color TEXT_COLOR   = new Color(230, 220, 200);
    public static final Color FIELD_BG     = new Color(15, 15, 25);
    public static final Color BTN_HOVER    = new Color(240, 200, 80);
}



