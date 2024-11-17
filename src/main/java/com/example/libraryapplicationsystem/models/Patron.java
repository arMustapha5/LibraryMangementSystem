package com.example.libraryapplicationsystem.models;
public class Patron {
    private int id;
    private String name;
    private String email;

    public Patron(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
