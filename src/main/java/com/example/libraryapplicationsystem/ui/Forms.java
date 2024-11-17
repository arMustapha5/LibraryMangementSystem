package com.example.libraryapplicationsystem.ui;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Forms {
    public static VBox createBookForm() {
        VBox form = new VBox(10);
        TextField titleField = new TextField();
        titleField.setPromptText("Enter Book Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Enter Book Author");
        form.getChildren().addAll(titleField, authorField);
        return form;
    }

    public static VBox createPatronForm() {
        VBox form = new VBox(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Enter Patron Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter Patron Email");
        form.getChildren().addAll(nameField, emailField);
        return form;
    }
}

