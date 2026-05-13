package PaooGame.Components;

import PaooGame.Graphics.AssetManager;
import PaooGame.Graphics.FontManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {
    private int[] SRC_BTN;
    private String buttonName;
    private BufferedImage cursorImg;
    private int SRC_W, SRC_H;
    private int wndW, wndH;

    public MenuButton(int[] SRC_BTN, String buttonName, BufferedImage cursorImg, int wndW, int wndH) {
        this.SRC_BTN = SRC_BTN;
        this.buttonName = buttonName;
        this.cursorImg = cursorImg;
        this.SRC_W = MenuConfig.startMenu_W;
        this.SRC_H = MenuConfig.startMenu_H;
        this.wndW = wndW;
        this.wndH = wndH;
    }

    public boolean contains(int mx, int my) {
        int[] b = scaledBtn();
        return mx >= b[0] && mx <= b[0] + b[2] && my >= b[1] && my <= b[1] + b[3];
    }

    private int[] scaledBtn() {
        int[] s = SRC_BTN;
        double sx = (double) wndW / SRC_W;
        double sy = (double) wndH / SRC_H;
        return new int[]{
                (int) (s[0] * sx), (int) (s[1] * sy),
                (int) (s[2] * sx), (int) (s[3] * sy)
        };
    }

    public void draw(Graphics2D g2d) {
        int[] b = scaledBtn();
        g2d.setFont(FontManager.getFont().deriveFont(22f));
        FontMetrics fm = g2d.getFontMetrics();
        int tx = b[0] + (b[2] - fm.stringWidth(buttonName)) / 2;
        int ty = b[1] + (b[3] - fm.getHeight()) / 2 + fm.getAscent();
        g2d.setColor(new Color(60, 30, 10));
        g2d.drawString(buttonName, tx, ty);
    }

    public void drawHoverButton(Graphics2D g2d) {
        int[] b = scaledBtn();
        if (cursorImg == AssetManager.mouseEast) {
            g2d.drawImage(cursorImg, b[0] - cursorImg.getWidth() - 5, b[1] + b[3] / 2 - cursorImg.getHeight() / 2, null);
        } else {
            g2d.drawImage(cursorImg, b[0] + b[2] + 5, b[1] + b[3] / 2 - cursorImg.getHeight() / 2, null);
        }
    }
}
