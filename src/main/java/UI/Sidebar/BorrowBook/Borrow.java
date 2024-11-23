package UI.Sidebar.BorrowBook;

import UI.Sidebar.Library.BookData.HashingMultipleBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Borrow {
//    private final ObjectProperty<Account> username = new SimpleObjectProperty<>();
//    private final ObjectProperty<Book> title = new SimpleObjectProperty<>();
    private String username;
    private String isbn;
    private String bookTitle;
    private String status;
    private final String borrowDate;
    public HashingMultipleBase hashUsername, hashIsbn, hashTitle;

    public Borrow(String username,String isbn, String bookTitle, String status) {
        this.username = username;
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.status = status;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentTime = LocalDate.now();
        String formattedTime = currentTime.format(formatter);
        this.borrowDate = formattedTime;
        hashUsername = hashInit(username);
        hashIsbn = hashInit(isbn);
        hashTitle = hashInit(bookTitle);
    }

    public Borrow(String[] data) {
        this.username = data[0];
        this.isbn = data[1];
        this.bookTitle = data[2];
        this.status = data[3];
        this.borrowDate = data[4];
        hashUsername = hashInit(username);
        hashIsbn = hashInit(isbn);
        hashTitle = hashInit(bookTitle);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public HashingMultipleBase hashInit(String info) {
        HashingMultipleBase hash = new HashingMultipleBase(info.length());
        hash.hash(info, 1);
        hash.hash(info, 2);
        return hash;
    }

    public ObservableValue<String> usernameProperty() {
        return new SimpleStringProperty(username);
    }

    public ObservableValue<String> isbnProperty() {
        return new SimpleStringProperty(isbn);
    }

    public ObservableValue<String> titleProperty() {
        return new SimpleStringProperty(bookTitle);
    }

    public ObservableValue<String> statusProperty() {
        return new SimpleStringProperty(status);
    }

    public ObservableValue<String> borrowedDateProperty() {
        return new SimpleStringProperty(borrowDate);
    }
}