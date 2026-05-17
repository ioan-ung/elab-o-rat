package PaooGame.Data;

import PaooGame.Levels.Level;
import PaooGame.Levels.LevelManager;

import java.awt.Point;
import java.util.ArrayList;
import java.util.logging.Logger;

import java.sql.*;

public class Database {
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());    // Trying this out

    private static final String URL = "jdbc:sqlite:game.db"; // URL of database
    private static Connection conn;         // Uses only one connection initialized during initDB
    private static int currentPlayerId = -1; //tine evidenta playerului curent

    public static void initDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn =  DriverManager.getConnection(URL);
            try (Statement stmt = conn.createStatement()) {
                // Initialize Player Table
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS player (" +
                                "id INTEGER PRIMARY KEY, " +
                                "currentLevel INTEGER, " +
                                "playerX INTEGER, " +
                                "playerY INTEGER, " +
                                "score INTEGER, " +
                                "name TEXT);"
                );
                stmt.execute(
                        "INSERT OR IGNORE INTO player (id, currentLevel, playerX, playerY, score, name) " +
                                "VALUES (1, 0, 0, 0, 0, '');"
                );
                // Initialize Game Object Table
                stmt.execute(
                        "CREATE TABLE IF NOT EXISTS coordinates (" +
                                "player_id INTEGER, " +
                                "x INTEGER, " +
                                "y INTEGER);"
                );
            }
        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Database initialization failed!", e);
        } catch (ClassNotFoundException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "SQLite JDBC driver JAR missing", e);
        }
    }

    public static void saveObjChanges(int x, int y) {
        if (currentPlayerId == -1) return;

        String sql = "INSERT INTO coordinates (player_id, x, y) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, currentPlayerId);
            ps.setInt(2, x);
            ps.setInt(3, y);
            ps.executeUpdate();

            System.out.println("[Database] Map change at (" + x + ", " + y + ")");

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed appending map change", e);
        }
    }

    public static ArrayList<Point> loadObjChanges() {
        ArrayList<Point> changedTiles = new ArrayList<>();
        if (currentPlayerId == -1) return changedTiles;

        String sql = "SELECT x, y FROM coordinates WHERE player_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, currentPlayerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) changedTiles.add(new Point(rs.getInt("x"), rs.getInt("y")));
            }
            System.out.println("[Database] Loaded " + changedTiles.size() + " permanent map changes.");

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed loading map changes", e);
        }
        return changedTiles;
    }

    public static void savePlayerState(int level, int x, int y, int score, String name) {
        if (currentPlayerId == -1) return;

        int oldLevel = getLevelIndex();
        // Wipe coordinates table when going to the next level
        if (level != oldLevel) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM coordinates;");
            } catch (SQLException e) {
                LOGGER.log(java.util.logging.Level.SEVERE, "Failed wiping coordinates on level change", e);
            }
        }

        String sql = "UPDATE player SET currentLevel = ?, playerX = ?, playerY = ?, score = ?, name = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, level);
            ps.setInt(2, x);
            ps.setInt(3, y);
            ps.setInt(4, score);
            ps.setString(5, name);
            ps.setInt(6, currentPlayerId);
            ps.executeUpdate();

            System.out.println("[Database] Game Saved Successfully!");

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
    }
    public static void savePlayerScore(int score) {
        String sql = "UPDATE player SET score = " +score+ " WHERE id = " + currentPlayerId;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.executeUpdate();

            System.out.println("[Database] Saved Player Score");

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
    }

    public static void loadPlayerState() {
        String sql = "SELECT playerX, playerY, score FROM player WHERE id = " + currentPlayerId + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                Level.player.move(rs.getInt("playerX"),rs.getInt("playerY"));
                Level.player.setScore(rs.getInt("score"));

                System.out.println("[Database] Loaded Player");
            }
        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
    }

    //idul se genereaza automat --metoda insert din sql
    public static void startNewGame(String name) {
        LevelManager.currentLevel = null;
        String sql = "INSERT INTO player (currentLevel, playerX, playerY, score, name) " +
                "VALUES (0, 64, 480, 0, ?);";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) currentPlayerId = keys.getInt(1);
            else currentPlayerId = -1;

            // Wipe coordinates table
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM coordinates;");
            }

            System.out.println("[Database] New Game");
        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
    }

    public static int getLevelIndex() {
        String sql = "SELECT currentLevel FROM player WHERE id = " + currentPlayerId + ";";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getInt("currentLevel");

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
        return 0;
    }

    public static String resumeLastGame() {
        String sql = "SELECT id, name, currentLevel FROM player WHERE name != '' ORDER BY id DESC LIMIT 1;";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                currentPlayerId = rs.getInt("id");
                LevelManager.currentLevelIndex = rs.getInt("currentLevel");
                return rs.getString("name");
            }

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
        return "";
    }

    public static boolean playerExists(String numeCautat) {
        String sql = "SELECT COUNT(*) FROM player WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeCautat);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
        return false;
    }

    public static String[][] getTopPlayers(int limit) {
        String sql = "SELECT name, score FROM player WHERE name != '' ORDER BY score DESC LIMIT " + limit + ";";
        String[][] result = new String[0][];

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            java.util.List<String[]> rows = new java.util.ArrayList<>();
            while (rs.next())
                rows.add(new String[]{ rs.getString("name"), String.valueOf(rs.getInt("score")) });
            result = rows.toArray(new String[0][]);
        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
        return result;
    }
}
