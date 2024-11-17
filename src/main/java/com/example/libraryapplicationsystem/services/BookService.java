package com.example.libraryapplicationsystem.services;

import com.example.libraryapplicationsystem.data.DatabaseConnector;
import com.example.libraryapplicationsystem.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM books");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(rs.getString("id"), rs.getString("title"), rs.getString("author")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public void addBook(Book book) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO books (title, author) VALUES (?, ?)");
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
