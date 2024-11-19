package com.example.libraryapplicationsystem.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/librarymanagementsystem";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Charles";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
            return null; // If the connection fails, return null.
        }
    }

    // Test method for database connection
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the PostgreSQL database successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}