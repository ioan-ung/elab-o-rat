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
        String text = "Press ESC to resume";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (w - fm.stringWidth(text)) / 2;
        int y = h / 2 + fm.getHeight();

        g2d.setColor(new Color(200, 200, 200));
        g2d.drawString(text, x, y);
    }
}
