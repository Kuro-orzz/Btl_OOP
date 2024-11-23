package UI.Sidebar.BorrowBook.BorrowedData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Borrowed {
    private final int id;
    private final String fullName;
    private final String isbn;
    private final String borrowedDate;
    private final String dueDate;
    private String status;

    public Borrowed(int id, String fullName, String isbn, String borrowedDate, String status) {
        this.id = id;
        this.fullName = fullName;
        this.isbn = isbn;
        this.borrowedDate = borrowedDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate currentTime = LocalDate.parse(borrowedDate, formatter).plusDays(30);
        String formattedTime = currentTime.format(formatter);
        this.dueDate = formattedTime;
        this.status = status;
    }

    public Borrowed(String[] data) {
        this.id = Integer.parseInt(data[0]);
        this.fullName = data[1];
        this.isbn = data[2];
        this.borrowedDate = data[3];
        this.dueDate = data[4];
        this.status = data[5];
    }

    public int getId() {
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
}