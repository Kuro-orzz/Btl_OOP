package UI.Sidebar.BorrowBook.BorrowedData;

import Optimize.HashingMultipleBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Borrowed extends HashingMultipleBase {
    private final String id;
    private final String fullName;
    private final String isbn;
    private final String borrowedDate;
    private final String dueDate;
    private String status;
    HashingMultipleBase hashId, hashFullName, hashIsbn, hashStatus;

    public Borrowed(String id, String fullName, String isbn, String borrowedDate, String status) {
        this.id = id;
        this.fullName = fullName;
        this.isbn = isbn;
        this.borrowedDate = borrowedDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentTime = LocalDate.parse(borrowedDate, formatter).plusDays(30);
        this.dueDate = currentTime.format(formatter);
        this.status = status;
        hashId = hashInit(id);
        hashFullName = hashInit(fullName);
        hashIsbn = hashInit(isbn);
        hashStatus = hashInit(status);
    }

    public Borrowed(String[] data) {
        this.id = data[0];
        this.fullName = data[1];
        this.isbn = data[2];
        this.borrowedDate = data[3];
        this.dueDate = data[4];
        this.status = data[5];
        hashId = hashInit(id);
        hashFullName = hashInit(fullName);
        hashIsbn = hashInit(isbn);
        hashStatus = hashInit(status);
    }

    public Borrowed(String id, String isbn) {
        this.id = id;
        this.isbn = isbn;
        this.fullName = "";
        this.borrowedDate = "";
        this.dueDate = "";
        this.status = "";
        hashId = hashInit(id);
        hashFullName = hashInit(fullName);
        hashIsbn = hashInit(isbn);
        hashStatus = hashInit(status);
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ObservableValue<String> idProperty() {
        return new SimpleStringProperty(id);
    }

    public ObservableValue<String> fullNameProperty() {
        return new SimpleStringProperty(fullName);
    }

    public ObservableValue<String> isbnProperty() {
        return new SimpleStringProperty(isbn);
    }

    public ObservableValue<String> borrowedDateProperty() {
        return new SimpleStringProperty(borrowedDate);
    }

    public ObservableValue<String> dueDateProperty() {
        return new SimpleStringProperty(dueDate);
    }

    public ObservableValue<String> statusProperty() {
        return new SimpleStringProperty(status);
    }
}