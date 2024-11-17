package com.example.libraryapplicationsystem.data;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS books (id INT PRIMARY KEY AUTO_INCREMENT, title VARCHAR(100), author VARCHAR(100))");
            stmt.execute("CREATE TABLE IF NOT EXISTS patrons (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), email VARCHAR(100))");
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (id INT PRIMARY KEY AUTO_INCREMENT, book_id INT, patron_id INT, date_issued DATE, due_date DATE, FOREIGN KEY(book_id) REFERENCES books(id), FOREIGN KEY(patron_id) REFERENCES patrons(id))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
