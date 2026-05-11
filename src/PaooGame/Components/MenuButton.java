package PaooGame.Components;

import PaooGame.Graphics.AssetManager;
import PaooGame.Menus.StartMenu;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {
    private int[]SRC_BTN;
    private BufferedImage cursorImg;
    private int SRC_W,SRC_H;
    private int wndW, wndH;
    private boolean hovered;

    public MenuButton(int[]SRC_BTN,BufferedImage cursorImg,int SRC_W, int SRC_H, int wndW, int wndH){
        this.SRC_BTN=SRC_BTN;
        this.cursorImg=cursorImg;
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

    public void drawHoverButton(Graphics2D g2d){
        int[] b = scaledBtn();

        if(cursorImg== AssetManager.mouseEast){
            g2d.drawImage(cursorImg, b[0] - cursorImg.getWidth() - 5, b[1] + b[3]/2 - cursorImg.getHeight()/2, null);
        }
        else{
            g2d.drawImage(cursorImg, b[0] + b[2] + 5, b[1] + b[3]/2 - cursorImg.getHeight()/2, null);
        }
    }

}
