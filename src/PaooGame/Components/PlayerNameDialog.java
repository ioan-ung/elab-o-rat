package PaooGame.Components;

import javax.swing.*;
import java.awt.*;

public class PlayerNameDialog
{
    public static String show(Component parent)
    {
        String name = JOptionPane.showInputDialog(
                null,
                "Enter your name:",
                "New Game",
                JOptionPane.PLAIN_MESSAGE
        );
        if(name != null && !name.trim().isEmpty())
            return name.trim();
        return null;
    }
}