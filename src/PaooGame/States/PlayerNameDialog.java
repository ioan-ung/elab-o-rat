package PaooGame.States;

import javax.swing.*;
public class PlayerNameDialog
{
    public static String show()
    {
        String name = JOptionPane.showInputDialog(
                null,
                "Enter your name:",
                "New Game",
                JOptionPane.PLAIN_MESSAGE
        );
        if(name != null && !name.trim().isEmpty())
            return name.trim();
        return null;  // player a dat Cancel
    }
}