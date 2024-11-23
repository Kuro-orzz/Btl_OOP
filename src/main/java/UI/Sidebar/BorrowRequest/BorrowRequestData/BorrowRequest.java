package UI.Sidebar.BorrowRequest.BorrowRequestData;

public class BorrowRequest {
    private final int id;
    private final String fullName;
    private final String isbn;
    private final String requestDate;
    private String status;

    public BorrowRequest(int id, String fullName, String isbn, String requestDate, String status) {
        this.id = id;
        this.fullName = fullName;
        this.isbn = isbn;
        this.requestDate = requestDate;
        this.status = status;
    }

    public BorrowRequest(String[] data) {
        this.id = Integer.parseInt(data[0]);
        this.fullName = data[1];
        this.isbn = data[2];
        this.requestDate = data[3];
        this.status = data[4];
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

    public String getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
