package com.example.libraryapplicationsystem.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty year;

    public Book(String title, String author, String year) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.year = new SimpleStringProperty(year);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public StringProperty yearProperty() {
        return year;
    }

    public String getTitle() {
        return title.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getYear() {
        return year.get();
    }
}