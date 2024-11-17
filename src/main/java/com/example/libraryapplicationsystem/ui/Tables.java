package com.example.libraryapplicationsystem.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.libraryapplicationsystem.models.Book;

public class Tables {
    public static TableView<Book> createBookTable() {
        TableView<Book> table = new TableView<>();
        TableColumn<Book, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        table.getColumns().addAll(idColumn, titleColumn, authorColumn);

        // Populate data
        ObservableList<Book> data = FXCollections.observableArrayList(
                new Book("No Sweetness Here", "Ama Ataa Aidoo", "2009"),
                new Book("Songs of Ice and Fire", "George R.R. Martin", "1972")
        );
        table.setItems(data);

        return table;
    }
}
