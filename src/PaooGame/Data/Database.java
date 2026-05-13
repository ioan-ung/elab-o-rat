package PaooGame.Data;

import PaooGame.Levels.Level;
import PaooGame.Levels.LevelManager;

import java.util.logging.Logger;

import java.sql.*;

public class Database {
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());    // Trying this out

    private static final String URL = "jdbc:sqlite:game.db";
    public static int currentPlayerId = -1;
    //tine evidenta playerului curent

    public static void initDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(URL);
                 Statement stmt = conn.createStatement()) {

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
                                "VALUES (1, 0, 64, 480, 0, '');"
                );
            }
        } catch (SQLException e) {
            System.err.println("Database initialization failed!\n" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver JAR missing\n" + e.getMessage());
        }
    }

    public static void savePlayerState(int level, int x, int y, int score, String name) {
        String sql = "UPDATE player SET currentLevel = ?, playerX = ?, playerY = ?, score = ?, name = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, level);
            pstmt.setInt(2, x);
            pstmt.setInt(3, y);
            pstmt.setInt(4, score);
            pstmt.setString(5, name);
            pstmt.setInt(6, currentPlayerId);
            pstmt.executeUpdate();

            System.out.println("[Database] Game Saved Successfully!");

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
    }
    public static void savePlayerScore(int score) {
        String sql = "UPDATE player SET score = " +score+ " WHERE id = " + currentPlayerId;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate();

            System.out.println("[Database] Game Saved Successfully!");

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
    }

    public static void loadPlayerState() {
        String sql = "SELECT playerX, playerY, score FROM player WHERE id = " + currentPlayerId + ";";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
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
    public static int startNewGame(String name) {
        LevelManager.currentLevel = null;
        String sql = "INSERT INTO player (currentLevel, playerX, playerY, score, name) " +
                "VALUES (0, 64, 480, 0, ?);";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
        return -1;
    }

    public static int getLevelIndex() {
        String sql = "SELECT currentLevel FROM player WHERE id = " + currentPlayerId + ";";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getInt("currentLevel");

        } catch (SQLException e) {
            LOGGER.log(java.util.logging.Level.SEVERE, "Failed during SQL", e);
        }
        return 0;
    }

    public static String resumeLastGame() {
        String sql = "SELECT id, name, currentLevel FROM player WHERE name != '' ORDER BY id DESC LIMIT 1;";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
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

    public static String[][] getTopPlayers(int limit) {
        String sql = "SELECT name, score FROM player WHERE name != '' ORDER BY score DESC LIMIT " + limit + ";";
        String[][] result = new String[0][];

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
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
