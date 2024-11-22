package Code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Borrow {
//    private final ObjectProperty<Account> username = new SimpleObjectProperty<>();
//    private final ObjectProperty<Book> title = new SimpleObjectProperty<>();
    private String username;
    private String bookTitle;
    private String status;
    private final String borrowDate;

    public Borrow(String username, String bookTitle, String status) {
        this.username = username;
        this.bookTitle = bookTitle;
        this.status = status;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentTime = LocalDate.now();
        String formattedTime = currentTime.format(formatter);
        this.borrowDate = formattedTime;
    }

    public Borrow(String[] data) {
        this.username = data[0];
        this.bookTitle = data[1];
        this.status = data[2];
        this.borrowDate = data[3];
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

//    public void setBorrowDate(String borrowDate) {
//        this.borrowDate = borrowDate;
//    }

//    public ObservableValue<Account> usernameProperty() {
//        return username;
//    }
//
//    public ObservableValue<Book> titleProperty() {
//        return title;
//    }

//    public ObservableValue<String> statusProperty() {
//        return new SimpleStringProperty(status);
//    }
//
//    public ObservableValue<LocalDate> borrowedDateProperty() {
//        return new SimpleObjectProperty<>(borrowedDate);
//    }
}