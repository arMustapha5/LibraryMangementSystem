package com.example.libraryapplicationsystem.models;

public class Patron {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Patron(int id, String name, String email, String phone) {
        this.id = this.id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return id + ": " + name + " (" + email + ", " + phone + ")";
    }
}
