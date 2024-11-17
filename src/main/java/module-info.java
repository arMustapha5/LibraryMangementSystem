module com.example.libraryapplicationsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    exports com.example.libraryapplicationsystem.ui;  // Exporting the UI package to javafx
    opens com.example.libraryapplicationsystem.ui to javafx.fxml;  // For FXML reflection (if you use it)
    // Add other required exports here for your other packages
}
