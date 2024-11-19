package com.example.libraryapplicationsystem.models;

import javafx.beans.property.SimpleStringProperty;

public class Patron {
    private int id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;

    public Patron (String name, String email, String phone) {
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
    }

    public int getId() {
        return id;
    }

    public SimpleStringProperty getName() {
        return name;
    }

    public SimpleStringProperty getEmail() {
        return email;
    }

    public SimpleStringProperty getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return id + ": " + name + " (" + email + ", " + phone + ")";
    }
}
