package UI.Sidebar.BorrowRequest.BorrowRequestData;

import Optimize.HashingMultipleBase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class BorrowRequest extends HashingMultipleBase {
    private final String id;
    private final String fullName;
    private final String isbn;
    private final String requestDate;
    private String status;
    private final BooleanProperty accept;
    private final BooleanProperty decline;
    HashingMultipleBase hashId, hashFullName, hashIsbn, hashRequestDate, hashStatus;

    public BorrowRequest(String id, String fullName, String isbn, String requestDate, String status) {
        this.id = id;
        this.fullName = fullName;
        this.isbn = isbn;
        this.requestDate = requestDate;
        this.status = status;
        this.accept = new SimpleBooleanProperty(false);
        this.decline = new SimpleBooleanProperty(false);
        hashId = hashInit(id);
        hashFullName = hashInit(fullName);
        hashIsbn = hashInit(isbn);
        hashRequestDate = hashInit(requestDate);
        hashStatus = hashInit(status);
    }

    public BorrowRequest(String[] data) {
        this.id = data[0];
        this.fullName = data[1];
        this.isbn = data[2];
        this.requestDate = data[3];
        this.status = data[4];
        this.accept = new SimpleBooleanProperty(false);
        this.decline = new SimpleBooleanProperty(false);
        hashId = hashInit(id);
        hashFullName = hashInit(fullName);
        hashIsbn = hashInit(isbn);
        hashRequestDate = hashInit(requestDate);
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

    public String getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getAccept() {
        return accept.get();
    }

    public void setAccept(boolean accept) {
        this.accept.set(accept);
    }

    public boolean getDecline() {
        return decline.get();
    }

    public void setDecline(boolean decline) {
        this.decline.set(decline);
    }

    public ObservableValue<String> idProperty() {
        return new SimpleStringProperty(String.valueOf(id));
    }

    public ObservableValue<String> fullNameProperty() {
        return new SimpleStringProperty(fullName);
    }

    public ObservableValue<String> isbnProperty() {
        return new SimpleStringProperty(isbn);
    }

    public ObservableValue<String> requestDateProperty() {
        return new SimpleStringProperty(requestDate);
    }

    public ObservableValue<String> statusProperty() {
        return new SimpleStringProperty(status);
    }

    public ObservableValue<Boolean> acceptProperty() {
        return accept;
    }

    public ObservableValue<Boolean> declineProperty() {
        return decline;
    }
}
