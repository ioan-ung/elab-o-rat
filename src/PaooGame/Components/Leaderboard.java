package PaooGame.Components;

import java.awt.*;

public class Leaderboard {

    private final String[] names;
    private final int[]    scores;

    public Leaderboard(String[] names, int[] scores) {
        this.names  = names;
        this.scores = scores;
    }

    public void draw(Graphics2D g2d, int wndWidth, int wndHeight) {
        int srcW = MenuConfig.leaderBoard_W;
        int srcH = MenuConfig.leaderBoard_H;

        double sx = (double) wndWidth  / srcW;
        double sy = (double) wndHeight / srcH;

        int lbX = (int)(MenuConfig.leaderBoard_X * sx);
        int lbY = (int)(MenuConfig.leaderBoard_Y * sy);
        int lbW = (int)(MenuConfig.leaderBoard_W * sx);
        int lbH = (int)(MenuConfig.leaderBoard_H * sy);

        drawBackground(g2d, lbX, lbY, lbW, lbH);
        drawTitle(g2d, lbX, lbY, lbW, lbH);
        drawRows(g2d, lbX, lbY, lbW, lbH);
    }

    private void drawBackground(Graphics2D g2d, int x, int y, int w, int h) {
        g2d.setColor(new Color(10, 10, 20, 200));
        g2d.fillRect(x, y, w, h);
        g2d.setColor(new Color(245, 200, 66, 200));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x, y, w, h);
    }

    private void drawTitle(Graphics2D g2d, int x, int y, int w, int h) {
        int fontSize = Math.max(10, (int)(h * 0.13));
        g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        g2d.setColor(new Color(245, 200, 66));
        g2d.drawString("LEADERBOARD", x + (int)(w * 0.1), y + (int)(h * 0.2));
    }

    private void drawRows(Graphics2D g2d, int x, int y, int w, int h) {
        int fontSize = Math.max(9, (int)(h * 0.11));
        g2d.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        g2d.setColor(new Color(220, 220, 220));

        int rows = Math.min(names.length, scores.length);
        for (int i = 0; i < rows; i++) {
            int rowY = y + (int)(h * (0.35 + i * 0.22));
            drawRow(g2d, x, rowY, w, i);
        }
    }

    private void drawRow(Graphics2D g2d, int x, int rowY, int w, int index) {
        String label    = (index + 1) + ". " + names[index];
        String scoreStr = String.valueOf(scores[index]);
        int scoreX = x + w - g2d.getFontMetrics().stringWidth(scoreStr) - (int)(w * 0.07);

        g2d.drawString(label,    x + (int)(w * 0.07), rowY);
        g2d.drawString(scoreStr, scoreX,               rowY);
    }
}
