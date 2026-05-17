package PaooGame.Components;

import PaooGame.Data.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlayerNameValidator {

    public static void showError(JLabel errorLabel, JTextField field, String message) {
        errorLabel.setText(message);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 60, 60), 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
    }

    // returneaza mesajul de eroare, sau null daca numele e valid
    public static String checkName(String name) {
        if (name.isEmpty()) return "Numele nu poate fi gol";
        if (name.length() < 3) return "Numele e prea scurt";
        if (name.length() > 20) return "Numele e prea lung";
        if (!name.matches("[a-zA-Z0-9]+")) return "Numele poate contine doar litere si cifre";
        if (Database.playerExists(name)) return "Playerul deja exista";
        return null;
    }

}
