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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

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
        Button btnBorrowBook = new Button("Borrow Book");


        // Event handlers for buttons
        btnAddBook.setOnAction(e -> showAddBookDialog());
        btnViewBooks.setOnAction(e -> showBooksList());
        btnViewPatrons.setOnAction(e -> showPatronsList());
        btnAddPatron.setOnAction(e -> showAddPatronDialog());
//        btnBorrowBook.setOnAction(e -> showBorrowBookForm());
        // Button layout
        VBox menu = new VBox(10, btnAddBook, btnViewBooks, btnViewPatrons, btnAddPatron, btnBorrowBook);
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

    private void showAddPatronDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Patron");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
            } else {
                addPatronToDatabase(name, email, phone);
                dialog.close();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Patron added successfully!");
            }
        });

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);
        grid.add(phoneField, 1, 2);
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

    private void showPatronsList() {
        Stage tableStage = new Stage();
        tableStage.setTitle("Patron List");

        TableView<Patron> tableView = new TableView<>();
        TableColumn<Patron, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> data.getValue().getName());
        TableColumn<Patron, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(data -> data.getValue().getEmail());
        TableColumn<Patron, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(data -> data.getValue().getPhone());

        tableView.getColumns().addAll(nameColumn, emailColumn, phoneColumn);

        ObservableList<Patron> patronList = fetchPatronsFromDatabase();
        tableView.setItems(patronList);

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

    private void addPatronToDatabase(String name, String email, String phone) {
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "INSERT INTO patrons (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
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

    private ObservableList<Patron> fetchPatronsFromDatabase() {
        ObservableList<Patron> patrons = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT * FROM patrons";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                patrons.add(new Patron(
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


