package PaooGame.Components;

import PaooGame.Menus.StartMenu;

import java.awt.*;

public class MenuButton {
    private int[]SRC_BTN;
    private int SRC_W,SRC_H;
    private int wndW, wndH;
    private boolean hovered;

    public MenuButton(int[]SRC_BTN, int SRC_W, int SRC_H, int wndW, int wndH){
        this.SRC_BTN=SRC_BTN;
        this.SRC_W=SRC_W;this.SRC_H=SRC_H;
        this.wndW = wndW;this.wndH = wndH;
    }

    public boolean contains(int mx, int my)
    {
        int[] b = scaledBtn();
        if(mx >= b[0] && mx <= b[0]+b[2] && my >= b[1] && my <= b[1]+b[3])
            return true;
        return false;
    }


    private int[] scaledBtn()
    {
        int[] s = SRC_BTN;

        double sx = (double) wndW / SRC_W;
        double sy = (double) wndH / SRC_H;

        return new int[] {
                (int)(s[0] * sx), (int)(s[1] * sy),
                (int)(s[2] * sx), (int)(s[3] * sy)
        };
    }

    public void drawHoverButton(Graphics2D g2d,int[] SRC_BTN){
        int[] b = scaledBtn();
        g2d.setColor(new Color(255, 200, 50, 120));
        g2d.fillRoundRect(b[0], b[1], b[2], b[3], 10, 10);
        g2d.setColor(new Color(220, 130, 20, 220));
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawRoundRect(b[0], b[1], b[2], b[3], 10, 10);
    }
}
