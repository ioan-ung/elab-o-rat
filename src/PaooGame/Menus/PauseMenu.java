package PaooGame.Menus;

import PaooGame.Graphics.FontManager;

import java.awt.*;

public class PauseMenu {

    public void draw(Graphics2D g2d, int wndWidth, int wndHeight) {
        drawOverlay(g2d, wndWidth, wndHeight);
        drawTitle(g2d, wndWidth, wndHeight);
        drawHint(g2d, wndWidth, wndHeight);
    }

    private void drawOverlay(Graphics2D g2d, int w, int h) {
        g2d.setColor(new Color(0, 0, 0, 160));
        g2d.fillRect(0, 0, w, h);
    }

    private void drawTitle(Graphics2D g2d, int w, int h) {
        int fontSize = Math.max(24, w / 14);
        g2d.setFont(FontManager.getFont().deriveFont(Font.PLAIN, fontSize));
        String text = "PAUSED";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (w - fm.stringWidth(text)) / 2;
        int y = h / 2 - fm.getHeight();

        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.drawString(text, x + 3, y + 3);

        g2d.setColor(new Color(245, 200, 66));
        g2d.drawString(text, x, y);
    }

    private void drawHint(Graphics2D g2d, int w, int h) {
        int fontSize = Math.max(12, w / 40);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        FontMetrics fm = g2d.getFontMetrics();

        String enterText = "Press ENTER for main menu";
        String escText   = "Press ESC to resume";

        int x1 = (w - fm.stringWidth(escText)) / 2;
        int x2 = (w - fm.stringWidth(enterText))   / 2;
        int y1 = h / 2 + fm.getHeight() * 2;
        int y2 = y1 + fm.getHeight() + 4;

        g2d.setColor(new Color(180, 180, 180));
        g2d.drawString(escText,   x1, y1-20);
        g2d.drawString(enterText, x2, y2);
    }
}
