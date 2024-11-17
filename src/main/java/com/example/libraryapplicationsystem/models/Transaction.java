package com.example.libraryapplicationsystem.models;
import java.sql.Date;

public class Transaction {
    private int id;
    private int bookId;
    private int patronId;
    private Date dateIssued;
    private Date dueDate;

    public Transaction(int id, int bookId, int patronId, Date dateIssued, Date dueDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.dateIssued = dateIssued;
        this.dueDate = dueDate;
    }

    public int getBookId() {
        return bookId;
    }


    public int getPatronId() {
        return patronId;
    }

    public java.util.Date getDateIssued() {
        return dateIssued;
    }

    public java.util.Date getDueDate() {
        return dueDate;
    }
}
