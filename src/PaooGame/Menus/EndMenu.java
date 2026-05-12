package PaooGame.Menus;

import java.awt.*;

public class EndMenu {

    public void draw(Graphics2D g2d, int wndWidth, int wndHeight, int score) {
        drawOverlay(g2d, wndWidth, wndHeight);
        drawTitle(g2d, wndWidth, wndHeight);
        drawScore(g2d, wndWidth, wndHeight, score);
        drawHint(g2d, wndWidth, wndHeight);
    }

    private void drawOverlay(Graphics2D g2d, int w, int h) {
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, w, h);
    }

    private void drawTitle(Graphics2D g2d, int w, int h) {
        int fontSize = Math.max(28, w / 12);
        g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        String text = "YOU WIN!";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (w - fm.stringWidth(text)) / 2;
        int y = h / 2 - fm.getHeight() * 2;

        g2d.setColor(new Color(0, 0, 0, 140));
        g2d.drawString(text, x + 3, y + 3);

        g2d.setColor(new Color(245, 200, 66));
        g2d.drawString(text, x, y);
    }

    private void drawScore(Graphics2D g2d, int w, int h, int score) {
        int fontSize = Math.max(16, w / 28);
        g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        String text = "Score: " + score;
        FontMetrics fm = g2d.getFontMetrics();
        int x = (w - fm.stringWidth(text)) / 2;
        int y = h / 2;

        g2d.setColor(new Color(220, 220, 220));
        g2d.drawString(text, x, y);
    }

    private void drawHint(Graphics2D g2d, int w, int h) {
        int fontSize = Math.max(12, w / 40);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        FontMetrics fm = g2d.getFontMetrics();

        String enterText = "ENTER  -  Return to menu";
        String escText   = "ESC    -  Quit";

        int x1 = (w - fm.stringWidth(enterText)) / 2;
        int x2 = (w - fm.stringWidth(escText))   / 2;
        int y1 = h / 2 + fm.getHeight() * 2;
        int y2 = y1 + fm.getHeight() + 4;

        g2d.setColor(new Color(180, 180, 180));
        g2d.drawString(enterText, x1, y1);
        g2d.drawString(escText,   x2, y2);
    }
}
