package com.example.libraryapplicationsystem.services;

import com.example.libraryapplicationsystem.data.DatabaseConnector;
import com.example.libraryapplicationsystem.models.Patron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatronService {
    public List<Patron> getAllPatrons() {
        List<Patron> patrons = new ArrayList<>();
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM patrons");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                patrons.add(new Patron(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patrons;
    }

    public void addPatron(Patron patron) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO patrons (name, email) VALUES (?, ?)");
            stmt.setString(1, patron.getName());
            stmt.setString(2, patron.getEmail());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

