package data;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;

/**
 *
 * @author hatuy
 * This is the database of the game
 */
public class DatabaseManager {
    //private static final String DB_URL = "jdbc:sqlite:resources/snake_game.db";
    
    private Connection conn;
    
    public Connection connect() {
        conn = null;
        try {
            File dbFile = new File("Source Packages/resources/snake_game.db");  
            if (!dbFile.exists()) {
                conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
                System.out.println("Database created: " + dbFile.getAbsolutePath());
                initializeDatabase(conn);
                System.out.println("OK");
            } else {
                conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }
    
    public void initializeDatabase(Connection conn) {
         try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS scores (" +
                                      "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                      "name TEXT NOT NULL, " +
                                      "score INTEGER NOT NULL)";
            conn.createStatement().execute(createTableQuery);
        } catch (SQLException e) {
            System.out.println("Error initializing the database: " + e.getMessage());
        }
    }
    
    public void saveScore(String name, int score) {
        try {
            String query = "INSERT INTO scores (name, score) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setInt(2, score);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTopScores() {
        StringBuilder result = new StringBuilder("");
        try {
            String query = "SELECT name, score FROM scores ORDER BY score DESC LIMIT 10";
            try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    result.append(rs.getString("name"))
                          .append(" - ")
                          .append(rs.getInt("score"))
                          .append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
