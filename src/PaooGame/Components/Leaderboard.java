package PaooGame.Components;

import PaooGame.Data.Database;

import java.awt.*;

import static PaooGame.Components.MenuConfig.*;

public class Leaderboard {

    public void draw(Graphics2D g2d, int wndWidth, int wndHeight) {
        double sx = (double) wndWidth  / MenuConfig.startMenu_W;
        double sy = (double) wndHeight / MenuConfig.startMenu_H;

        int lbX = (int)(leaderBoard_X * sx);
        int lbY = (int)(leaderBoard_Y * sy);
        int lbW = (int)(leaderBoard_W * sx);
        int lbH = (int)(leaderBoard_H * sy);

        //apelul catre database
        //cele mai bune 3 scoruri
        String[][] players = Database.getTopPlayers(3);

        drawTitle(g2d, lbX, lbY, lbW, lbH);
        for (int i = 0; i < players.length; i++)
            drawRow(g2d, lbX, lbY, lbW, lbH, i, players[i][0], Integer.parseInt(players[i][1]));
    }

    private void drawTitle(Graphics2D g2d, int x, int y, int w, int h) {
        int fontSize = Math.max(10, (int)(h * 0.13));
        g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        g2d.setColor(new Color(245, 200, 66));
        g2d.drawString("LEADERBOARD", x + (int)(w * 0.1), y + (int)(h * 0.2));
    }

    private void drawRow(Graphics2D g2d, int x, int y, int w, int h, int index, String name, int score) {
        int fontSize = Math.max(9, (int)(h * 0.11));
        g2d.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        g2d.setColor(new Color(220, 220, 220));

        int rowY = y + (int)(h * (0.38 + index * 0.22));
        String label    = (index + 1) + ". " + name;
        String scoreStr = String.valueOf(score);
        int scoreX = x + w - g2d.getFontMetrics().stringWidth(scoreStr) - (int)(w * 0.07);

        g2d.drawString(label,    x + (int)(w * 0.07), rowY);
        g2d.drawString(scoreStr, scoreX, rowY);
    }
}
