package com.example.libraryapplicationsystem.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.libraryapplicationsystem.data.DatabaseConnector;
import com.example.libraryapplicationsystem.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibraryUI extends Application {

    public static void launchApp() {
    }

    @Override
    public void start(Stage primaryStage) {
        // Create layout
        BorderPane root = new BorderPane();

        // Welcome Label
        Label welcomeLabel = new Label("Welcome to Library Management System");
        welcomeLabel.setStyle("-fx-font-size: 16pt; -fx-font-weight: bold;");
        root.setTop(welcomeLabel);
        BorderPane.setMargin(welcomeLabel, new Insets(10));

        // Create buttons
        Button btnAddBook = new Button("Add Book");
        Button btnViewBooks = new Button("View Books");

        // Event handlers for buttons
        btnAddBook.setOnAction(e -> showAddBookDialog());
        btnViewBooks.setOnAction(e -> showBooksList());

        // Button layout
        VBox menu = new VBox(10, btnAddBook, btnViewBooks);
        menu.setPadding(new Insets(15));
        root.setCenter(menu);

        // Create scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library Management System");
        primaryStage.show();
    }

    private void showAddBookDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Book");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField yearField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String year = yearField.getText();

            if (title.isEmpty() || author.isEmpty() || year.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
            } else {
                addBookToDatabase(title, author, year);
                dialog.close();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
            }
        });

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("Year:"), 0, 2);
        grid.add(yearField, 1, 2);
        grid.add(submitButton, 1, 3);

        Scene dialogScene = new Scene(grid, 400, 300);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showBooksList() {
        Stage tableStage = new Stage();
        tableStage.setTitle("Book List");

        TableView<Book> tableView = new TableView<>();
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(data -> data.getValue().authorProperty());
        TableColumn<Book, String> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(data -> data.getValue().yearProperty());

        tableView.getColumns().addAll(titleColumn, authorColumn, yearColumn);

        ObservableList<Book> bookList = fetchBooksFromDatabase();
        tableView.setItems(bookList);

        VBox vbox = new VBox(tableView);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 500, 400);
        tableStage.setScene(scene);
        tableStage.show();
    }

    private void addBookToDatabase(String title, String author, String year) {
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, year);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Book> fetchBooksFromDatabase() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT * FROM books";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("year")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        DatabaseConnector.connect(); // Ensure database connection works
        launch(args);
    }
}