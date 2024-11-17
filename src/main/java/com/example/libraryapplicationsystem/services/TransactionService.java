package com.example.libraryapplicationsystem.services;

import com.example.libraryapplicationsystem.data.DatabaseConnector;
import com.example.libraryapplicationsystem.models.Transaction;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class TransactionService {
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM transactions");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("patron_id"),
                        rs.getDate("date_issued"),
                        rs.getDate("due_date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO transactions (book_id, patron_id, date_issued, due_date) VALUES (?, ?, ?, ?)"
            );
            stmt.setInt(1, transaction.getBookId());
            stmt.setInt(2, transaction.getPatronId());
            stmt.setDate(3, new java.sql.Date(transaction.getDateIssued().getTime()));
            stmt.setDate(4, new java.sql.Date(transaction.getDueDate().getTime()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showAlert(Alert.AlertType alertType, String databaseError, String s) {
    }

}
