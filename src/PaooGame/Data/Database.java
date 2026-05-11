package PaooGame.Data;

import PaooGame.Levels.Level;
import PaooGame.Levels.LevelManager;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:game.db";

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
                                "score INTEGER);"
                );
                stmt.execute(
                        "INSERT OR IGNORE INTO player (id, currentLevel, playerX, playerY, score) " +
                                "VALUES (1, 0, 0, 0, 0);"
                );
            }
        } catch (SQLException e) {
            System.err.println("Database initialization failed!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver JAR missing");
            e.printStackTrace();
        }
    }

    public static void savePlayerState(int level, int x, int y, int score) {
        String sql = "UPDATE player SET currentLevel = ?, playerX = ?, playerY = ?, score = ? WHERE id = 1;";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, level);
            pstmt.setInt(2, x);
            pstmt.setInt(3, y);
            pstmt.setInt(4, score);
            pstmt.executeUpdate();

            System.out.println("[Database] Game Saved Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int loadLevelIndex() {
        String sql = "SELECT currentLevel FROM player WHERE id = 1;";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) return rs.getInt("currentLevel");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void loadPlayerState() {
        String sql = "SELECT playerX, playerY, score FROM player WHERE id = 1;";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                Level.player.move(rs.getInt("playerX"),rs.getInt("playerY"));
                Level.player.setScore(rs.getInt("score"));

                System.out.println("[Database] Loaded Player");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void startNewGame() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("UPDATE player SET currentLevel = 0, playerX = 64, playerY = 480, score = 0 WHERE id = 1;");
            System.out.println("[Database] Started a new game!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
