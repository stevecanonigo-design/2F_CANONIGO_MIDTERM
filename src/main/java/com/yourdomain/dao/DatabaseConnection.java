package com.yourdomain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // scenario.db sits in the project root (next to pom.xml)
    private static final String URL = "jdbc:sqlite:scenario.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    /** Creates the table if it does not exist yet. */
    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS pets ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "pet_name TEXT NOT NULL, "
                + "species TEXT NOT NULL CHECK (species IN ('DOG','CAT','BIRD')), "
                + "vaccine TEXT NOT NULL CHECK (vaccine IN ('RABIES','DHPP','FVRCP')), "
                + "fee REAL NOT NULL CHECK (fee >= 0))";
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println("Database initialization error: " + e.getMessage());
        }
    }
}
