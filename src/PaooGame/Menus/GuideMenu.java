package PaooGame.Menus;

import PaooGame.Components.MenuConfig;
import PaooGame.Graphics.FontManager;

import java.awt.*;

// meniul de ghid - afiseaza controalele si regulile jocului
// nu stiu daca e cel mai bun loc pt clasa asta dar merge
public class GuideMenu {

    private int scrollY = 0;     // cat am scrollat pana acum
    private int maxScroll = 0;   // calculat la draw, nu stiu daca e ok sa il tin aici
    private static final int SCROLL_STEP = 25;  // cat scrollam la fiecare notch de mouse
    private static final float PANEL_W_RATIO = 0.72f;   // 72% din fereastra
    private static final float PANEL_H_RATIO = 0.84f;   // si 84% inaltime, parut ok la 1280x720
    private final Rectangle closeBtnBounds = new Rectangle(); // tinem minte unde e X-ul pt click

    // resetam cand deschidem meniul, altfel ramane unde a lasat userul ultima oara
    public void resetScroll() { scrollY = 0; }

    public void scroll(int amount) {
        // amount vine negativ cand scrollezi in jos (de la mousewheel), de-aia e asa
        scrollY = Math.max(0, Math.min(scrollY + amount * SCROLL_STEP, maxScroll));
    }

    public boolean isCloseClicked(int mx, int my) {
        return closeBtnBounds.contains(mx, my);
    }

    // calculeaza dreptunghiul panoului central, centrat pe fereastra
    private Rectangle getPanelBounds(int wndW, int wndH) {
        int panelW = (int)(wndW * PANEL_W_RATIO);
        int panelH = (int)(wndH * PANEL_H_RATIO);
        return new Rectangle((wndW - panelW) / 2, (wndH - panelH) / 2, panelW, panelH);
    }

    // folosit ca sa inchidem meniul daca userul da click in afara lui
    public boolean isOutsidePanel(int mx, int my, int wndW, int wndH) {
        return !getPanelBounds(wndW, wndH).contains(mx, my);
    }

    public void draw(Graphics2D g2d, int wndW, int wndH) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // overlay negru semi-transparent peste tot - face sa para ca e un modal
        g2d.setColor(new Color(0, 0, 0, 210));
        g2d.fillRect(0, 0, wndW, wndH);

        Rectangle pb = getPanelBounds(wndW, wndH);
        int panelX = pb.x, panelY = pb.y, panelW = pb.width, panelH = pb.height;

        // desenam panoul principal
        g2d.setColor(MenuConfig.BG_COLOR);
        g2d.fillRoundRect(panelX, panelY, panelW, panelH, 24, 24);
        g2d.setColor(MenuConfig.ACCENT_COLOR);
        g2d.setStroke(new BasicStroke(2f));
        g2d.drawRoundRect(panelX, panelY, panelW, panelH, 24, 24);  // border

        // titlu centrat sus - marimea fontului depinde de rezolutie
        int titleFontSize = Math.max(16, wndW / 50);
        g2d.setFont(FontManager.getFont().deriveFont(Font.PLAIN, (float) titleFontSize));
        FontMetrics fmTitle = g2d.getFontMetrics();
        String title = "GAME GUIDE";
        g2d.setColor(MenuConfig.ACCENT_COLOR);
        g2d.drawString(title, panelX + (panelW - fmTitle.stringWidth(title)) / 2,
                       panelY + fmTitle.getAscent() + 10);

        // linie separatoare sub titlu
        int sepY = panelY + fmTitle.getHeight() + 16;
        g2d.setColor(new Color(80, 70, 30));
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawLine(panelX + 20, sepY, panelX + panelW - 20, sepY);

        // butonul X de inchidere - coltul dreapta sus
        // TODO: poate ar trebui sa fie mai mare, e cam mic pe ecrane mici
        int btnSize = 22;
        int btnX = panelX + panelW - btnSize - 12;
        int btnY = panelY + 10;
        closeBtnBounds.setBounds(btnX, btnY, btnSize, btnSize);  // update bounds pt detectie click
        g2d.setColor(new Color(170, 45, 45));  // rosu inchis
        g2d.fillRoundRect(btnX, btnY, btnSize, btnSize, 8, 8);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics fmX = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.drawString("X", btnX + (btnSize - fmX.stringWidth("X")) / 2,
                       btnY + fmX.getAscent() + (btnSize - fmX.getHeight()) / 2);

        // zona de continut scrollabil
        int scrollBarW = 10;
        int contentAreaTop = sepY + 8;
        int contentAreaH = panelH - (contentAreaTop - panelY) - 12;
        int contentX = panelX + 24;
        int contentW = panelW - 48 - scrollBarW - 6;  // lasam loc pt scrollbar la dreapta

        // clip ca sa nu iasa textul din panou
        Shape oldClip = g2d.getClip();
        g2d.setClip(panelX + 1, contentAreaTop, panelW - 2 - scrollBarW - 4, contentAreaH);

        int baseFont = Math.max(11, wndW / 100);
        int y = contentAreaTop + 12 - scrollY;  // scadem scrollY ca sa "miste" continutul

        // sectiunile de continut - ordine: poveste -> obiectiv -> controale -> obiecte -> inamici -> niveluri -> scor -> debug
        y = drawSection(g2d, "STORY", new String[]{
            "You play as a mouse on a mission to collect all the cheese.",
            "Navigate through 3 challenging levels full of puzzles and dangers."
        }, contentX, y, contentW, baseFont);

        y = drawSection(g2d, "OBJECTIVE", new String[]{
            "Collect ALL cheese in each level to advance to the next one.",
            "Complete all 3 levels to WIN and earn a place on the leaderboard!"
        }, contentX, y, contentW, baseFont);

        // tastele de miscare
        y = drawKeyTable(g2d, "MOVEMENT", new String[][]{
            {"W  /  UP",    "Move up"},
            {"S  /  DOWN",  "Move down"},
            {"A  /  LEFT",  "Move left"},
            {"D  /  RIGHT", "Move right"}
        }, contentX, y, contentW, baseFont);

        y = drawKeyTable(g2d, "GAME CONTROLS", new String[][]{
            {"ESC",   "Pause / Resume the game"},
            {"ENTER", "Confirm / Return to Main Menu"},
            {"M", "Toggle music on / off"},
            {"F4",    "Toggle fullscreen mode"}
        }, contentX, y, contentW, baseFont);

        // obiectele interactive din joc - am pus totul intr-o sectiune, poate ar fi mai clar separat?
        y = drawSection(g2d, "INTERACTIVE OBJECTS", new String[]{
            "Cheese        Collect each one for +10 points",
            "Button        Permanently opens a linked door when stepped on",
            "Timer Button  Opens a door temporarily; auto-closes after countdown",
            "Box Button    Door stays open only while a box is on the button",
            "Box           Can be pushed by the player to solve puzzles"
        }, contentX, y, contentW, baseFont);

        // inamicul pisica - BFS pathfinding, destul de nasol daca nu stii harta
        y = drawSection(g2d, "ENEMIES", new String[]{
            "Mice: Interacts with puzzle elements and may steal collectible cheese;",
            "      Appears in the Tutorial and Laboratory levels inside puzzles.",
            " Cat: Uses BFS pathfinding to hunt you down through the level;",
            "      Each hit costs -10 points (score never drops below 0);",
            "      Appears in the Maze level after you collect 3 cheese."
        }, contentX, y, contentW, baseFont);

        y = drawSection(g2d, "LEVELS", new String[]{
            "1. Tutorial     Learn movement, collect cheese, use buttons & doors.",
            "2. Laboratory   Solve bigger and more complex puzzles.",
            "3. Maze         Face the cat enemy with randomized cheese placement."
        }, contentX, y, contentW, baseFont);

        y = drawSection(g2d, "SCORING", new String[]{
            "+10 points  per cheese collected",
            "-10 points  per cat hit  (minimum score: 0)",
            "Final score is saved to the leaderboard when you complete all levels."
        }, contentX, y, contentW, baseFont);

        // debug mode - de lasat doar pt testare, nu pt release (dar cine stie)
        y = drawKeyTable(g2d, "DEBUG MODE  (toggle with F3)", new String[][]{
            {"F1",    "Skip to the next level"},
            {"SPACE", "Player speed changes to one tile per frame"},
            {"B",     "Spawn a box at the player position"},
            {"O",     "Open all doors around the player"},
            {"F6",    "Save current game state"},
            {"F7",    "Load saved game state"}
        }, contentX, y, contentW, baseFont);

        y += 16;
        // calculam maxScroll dupa ce am desenat tot
        maxScroll = Math.max(0, y + scrollY - contentAreaTop - contentAreaH);

        g2d.setClip(oldClip);  // restauram clip-ul original

        drawScrollBar(g2d, panelX + panelW - scrollBarW - 8, contentAreaTop + 4,
                      scrollBarW, contentAreaH - 8);

        // hint "scroll for more" - apare doar daca mai e continut jos
        if (maxScroll > 0 && scrollY < maxScroll) {
            g2d.setFont(new Font("SansSerif", Font.PLAIN, Math.max(9, wndW / 120)));
            g2d.setColor(new Color(130, 130, 130));
            String hint = "scroll for more  ▼";
            FontMetrics fmHint = g2d.getFontMetrics();
            g2d.drawString(hint, panelX + (panelW - fmHint.stringWidth(hint)) / 2,
                           panelY + panelH - 6);
        }
    }

    // deseneaza o sectiune cu header si lista de linii de text
    // returneaza noua pozitie y dupa ce a terminat
    private int drawSection(Graphics2D g2d, String header, String[] lines,
                            int x, int y, int w, int baseFont) {
        // header cu font mai mare
        g2d.setFont(FontManager.getFont().deriveFont(Font.PLAIN, (float)(baseFont + 3)));
        FontMetrics fmH = g2d.getFontMetrics();
        g2d.setColor(MenuConfig.ACCENT_COLOR);
        g2d.drawString(header, x, y + fmH.getAscent());
        y += fmH.getHeight() + 2;

        // linie subtire sub header
        g2d.setColor(new Color(80, 70, 30));
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawLine(x, y, x + w, y);
        y += 6;

        // continut cu font monospace ca sa se alinieze coloanele
        g2d.setFont(new Font("Monospaced", Font.PLAIN, baseFont));
        FontMetrics fmL = g2d.getFontMetrics();
        g2d.setColor(MenuConfig.TEXT_COLOR);
        for (String line : lines) {
            g2d.drawString(line, x + 10, y + fmL.getAscent());
            y += fmL.getHeight() + 2;
        }
        return y + 14;  // spatiu extra intre sectiuni
    }

    // similar cu drawSection dar fiecare rand are un "badge" pt tasta
    // arata mai bine decat simplu text pt controale
    private int drawKeyTable(Graphics2D g2d, String header, String[][] rows,
                             int x, int y, int w, int baseFont) {
        g2d.setFont(FontManager.getFont().deriveFont(Font.PLAIN, (float)(baseFont + 3)));
        FontMetrics fmH = g2d.getFontMetrics();
        g2d.setColor(MenuConfig.ACCENT_COLOR);
        g2d.drawString(header, x, y + fmH.getAscent());
        y += fmH.getHeight() + 2;

        g2d.setColor(new Color(80, 70, 30));
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawLine(x, y, x + w, y);
        y += 6;

        g2d.setFont(new Font("Monospaced", Font.PLAIN, baseFont));
        FontMetrics fmL = g2d.getFontMetrics();
        int rowH = fmL.getHeight() + 8;  // inaltime rand cu putin padding

        for (String[] row : rows) {
            String key    = row[0];
            String action = row[1];

            // badge pt tasta - dreptunghi rotunjit cu border
            int badgeW = fmL.stringWidth(key) + 14;
            int badgeH = fmL.getHeight() + 4;
            int badgeX = x + 10;
            int badgeY = y + (rowH - badgeH) / 2;  // centrat vertical in rand

            g2d.setColor(new Color(38, 38, 60));  // fundal inchis pt badge
            g2d.fillRoundRect(badgeX, badgeY, badgeW, badgeH, 6, 6);
            g2d.setColor(MenuConfig.ACCENT_COLOR);
            g2d.setStroke(new BasicStroke(1f));
            g2d.drawRoundRect(badgeX, badgeY, badgeW, badgeH, 6, 6);
            g2d.setColor(Color.WHITE);
            g2d.drawString(key, badgeX + 7, badgeY + fmL.getAscent() + 2);

            // sageata intre tasta si actiune
            int arrowX = badgeX + badgeW + 8;
            g2d.setColor(new Color(180, 160, 80));
            g2d.drawString("→", arrowX, badgeY + fmL.getAscent() + 2);

            // textul actiunii
            g2d.setColor(MenuConfig.TEXT_COLOR);
            g2d.drawString(action, arrowX + fmL.stringWidth("→") + 6,
                           badgeY + fmL.getAscent() + 2);

            y += rowH;
        }
        return y + 10;
    }

    // scrollbar simplu - track + thumb
    private void drawScrollBar(Graphics2D g2d, int x, int y, int w, int h) {
        // track (fundalul scrollbar-ului)
        g2d.setColor(new Color(32, 32, 48));
        g2d.fillRoundRect(x, y, w, h, w, w);

        if (maxScroll <= 0) return;  // nu avem ce scrolla, gata

        // calculam pozitia si inaltimea thumb-ului proportional cu continutul
        int totalContent = h + maxScroll;
        int thumbH = Math.max(20, (int)((float) h * h / totalContent));  // minim 20px ca sa poata fi apasat
        float ratio = (float) scrollY / maxScroll;
        int thumbY = y + (int)((h - thumbH) * ratio);

        g2d.setColor(MenuConfig.ACCENT_COLOR);
        g2d.fillRoundRect(x, thumbY, w, thumbH, w, w);
    }
}
