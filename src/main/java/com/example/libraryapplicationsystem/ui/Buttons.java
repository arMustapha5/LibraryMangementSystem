package com.example.libraryapplicationsystem.ui;

import javafx.scene.control.Button;

public class Buttons {
    public static Button createActionButton(String label, Runnable action) {
        Button button = new Button(label);
        button.setOnAction(event -> action.run());
        return button;
    }
}
