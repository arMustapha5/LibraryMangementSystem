package com.example.libraryapplicationsystem.ui;

import com.example.libraryapplicationsystem.models.Patron;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
        Button btnViewPatrons = new Button("View Patrons");
        Button btnAddPatron = new Button("Add Patron");

        // Event handlers for buttons
        btnAddBook.setOnAction(e -> showAddBookDialog());
        btnViewBooks.setOnAction(e -> showBooksList());
        btnViewPatrons.setOnAction(e -> showPatronList());
        btnAddPatron.setOnAction(e -> showAddPatronForm());

        // Button layout
        VBox menu = new VBox(10, btnAddBook, btnViewBooks, btnViewPatrons, btnAddPatron);
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



    private void addPatronToDatabase(String name, String email, String phone) {
        try (Connection connection = DatabaseConnector.connect()) {
            if (connection == null) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Unable to connect to the database.");
                return;
            }

            String query = "INSERT INTO patrons (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Patron added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAddPatronForm() {
        Stage stage = new Stage();
        stage.setTitle("Add Patron");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");

        Button addButton = new Button("Add Patron");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            addPatronToDatabase(name, email, phone);
            stage.close();
        });

        layout.getChildren().addAll(new Label("Add New Patron"), nameField, emailField, phoneField, addButton);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void showPatronList() {
        try (Connection connection = DatabaseConnector.connect()) {
            if (connection == null) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Unable to connect to the database.");
                return;
            }

            String query = "SELECT * FROM patrons";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            Stage stage = new Stage();
            stage.setTitle("Patron List");

            TableView<Patron> table = new TableView<>();

            TableColumn<Patron, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Patron, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Patron, String> emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            TableColumn<Patron, String> phoneColumn = new TableColumn<>("Phone");
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

            ObservableList<Patron> patrons = FXCollections.observableArrayList();
            while (resultSet.next()) {
                patrons.add(new Patron(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                ));
            }

            table.setItems(patrons);
            table.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn);

            VBox layout = new VBox(table);
            Scene scene = new Scene(layout, 600, 400);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


