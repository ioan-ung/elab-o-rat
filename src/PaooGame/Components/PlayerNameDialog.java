package PaooGame.Components;

import PaooGame.Graphics.FontManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import static PaooGame.Components.MenuConfig.*;

public class PlayerNameDialog {

    // buton stilizat cu hover
    private static JButton makeBtn(String text, Color normal, Color hover, Color fg, Dimension size) {
        JButton b = new JButton(text) {
            boolean hov = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hov = true;  repaint(); }
                public void mouseExited(MouseEvent e)  { hov = false; repaint(); }
            }); }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hov || !normal.equals(new Color(0,0,0,0))) {
                    g2.setColor(hov ? hover : normal);
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                }
                g2.setColor(fg);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(), (getWidth()-fm.stringWidth(getText()))/2, (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(size);
        return b;
    }

    public static String show(Component parent) {
        JDialog dlg = new JDialog((Frame) null, true);
        dlg.setUndecorated(true);
        dlg.setBackground(new Color(0,0,0,0));

        String[] result = { null };

        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_COLOR);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(ACCENT_COLOR);
                g2.setStroke(new BasicStroke(2f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 20, 20));
                g2.dispose();
            }
        };
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(24, 32, 24, 32));

        JLabel title = new JLabel("NEW GAME", SwingConstants.CENTER);
        title.setFont(FontManager.getFont().deriveFont(Font.BOLD, 26f));
        title.setForeground(ACCENT_COLOR);
        title.setBorder(new EmptyBorder(0,0,16,0));

        JLabel lbl = new JLabel("Enter your name:", SwingConstants.CENTER);
        lbl.setFont(FontManager.getFont().deriveFont(Font.PLAIN, 16f));
        lbl.setForeground(TEXT_COLOR);
        lbl.setBorder(new EmptyBorder(0,0,10,0));

        JTextField field = new JTextField(16) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FIELD_BG);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                super.paintComponent(g);
                g2.dispose();
            }
        };
        field.setOpaque(false);
        field.setFont(FontManager.getFont().deriveFont(Font.PLAIN, 18f));
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(ACCENT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 1, true), new EmptyBorder(6,12,6,12)));
        field.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btn  = makeBtn("PLAY", ACCENT_COLOR, BTN_HOVER, BG_COLOR, new Dimension(120, 38));
        btn.setFont(FontManager.getFont().deriveFont(Font.BOLD, 16f));

        JButton xBtn = makeBtn("✕", new Color(0,0,0,0), new Color(200,60,60), new Color(180,160,120), new Dimension(28, 28));
        xBtn.setFont(new Font("Arial", Font.BOLD, 14));

        ActionListener confirm = e -> {
            String name = field.getText().trim();
            if (!name.isEmpty()) { result[0] = name; dlg.dispose(); }
            else field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200,60,60), 1, true), new EmptyBorder(6,12,6,12)));
        };
        btn.addActionListener(confirm);
        field.addActionListener(confirm);
        xBtn.addActionListener(e -> dlg.dispose());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.add(xBtn, BorderLayout.EAST);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        for (JComponent c : new JComponent[]{title, lbl, field, btn})
            c.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(title);
        center.add(lbl);
        center.add(field);
        center.add(Box.createVerticalStrut(16));
        center.add(btn);

        root.add(topBar, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        dlg.setContentPane(root);
        dlg.pack();
        dlg.setLocationRelativeTo(parent);
        dlg.setShape(new RoundRectangle2D.Float(0, 0, dlg.getWidth(), dlg.getHeight(), 20, 20));

        // popup-ul urmeaza fereastra cand e mutata
        Window owner = SwingUtilities.getWindowAncestor(parent);
        ComponentListener mover = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) { dlg.setLocationRelativeTo(parent); }
        };
        if (owner != null) owner.addComponentListener(mover);
        dlg.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) { if (owner != null) owner.removeComponentListener(mover); }
        });

        SwingUtilities.invokeLater(field::requestFocusInWindow);
        dlg.setVisible(true);
        return result[0];
    }
}
