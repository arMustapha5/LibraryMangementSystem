package com.example.libraryapplicationsystem.services;

import com.example.libraryapplicationsystem.models.Patron;
import com.example.libraryapplicationsystem.data.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatronService {

    public ObservableList<Patron> getAllPatrons() {
        ObservableList<Patron> patrons = FXCollections.observableArrayList();
        String query = "SELECT * FROM patrons";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                patrons.add(new Patron(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patrons;
    }
}