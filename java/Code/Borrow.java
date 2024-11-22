package Code;

import AccountData.Account;
import BookData.Book;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;

public class Borrow {
    private final ObjectProperty<Account> username = new SimpleObjectProperty<>();
    private final ObjectProperty<Book> title = new SimpleObjectProperty<>();
    private String status;
    private LocalDate borrowedDate;

    public Borrow(Account username, Book title, String status, LocalDate borrowedDate) {
        this.username.set(username);
        this.title.set(title);
        this.status = status;
        this.borrowedDate = borrowedDate;
    }

    public Account getuserName() {
        return username.get();
    }

    public void setuserName(Account userName) {
        this.username.set(userName);
    }

    public Book getTitle() {
        return title.get();
    }

    public void setTitle(Book title) {
        this.title.set(title);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getborrowedDate() {
        return borrowedDate;
    }

    public void setborrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public ObservableValue<Account> usernameProperty() {
        return username;
    }

    public ObservableValue<Book> titleProperty() {
        return title;
    }

    public ObservableValue<String> statusProperty() {
        return new SimpleStringProperty(status);
    }

    public ObservableValue<LocalDate> borrowedDateProperty() {
        return new SimpleObjectProperty<>(borrowedDate);
    }
}
