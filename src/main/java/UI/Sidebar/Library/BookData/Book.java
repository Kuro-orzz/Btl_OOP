package UI.Sidebar.Library.BookData;

import Optimize.HashingMultipleBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Book extends HashingMultipleBase {
    private String isbn;
    private String title;
    private String author;
    private String yearOfPublication;
    private String publisher;
    private String quantity;
    public HashingMultipleBase hashIsbn, hashTitle, hashAuthor, hashYearOfPublication, hashPublisher;

    public Book() {}

    /**
     * Full constructor.
     * @param isbn isbn
     * @param title title
     * @param author author
     * @param yearOfPublication year of publication
     * @param publisher publisher
     * @param quantity quantity
     */
    public Book(String isbn, String title, String author, String yearOfPublication, String publisher, String quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
        this.publisher = publisher;
        this.quantity = quantity;
        hashIsbn = hashInit(isbn);
        hashTitle = hashInit(title);
        hashAuthor = hashInit(author);
        hashYearOfPublication = hashInit(yearOfPublication);
        hashPublisher = hashInit(publisher);
    }

    /**
     * Create book from String Array.
     *
     * @param data String Array
     */
    public Book(String[] data) {
        this.isbn = data[0];
        this.title = data[1];
        this.author = data[2];
        this.yearOfPublication = data[3];
        this.publisher = data[4];
        this.quantity = data[5];
        hashIsbn = hashInit(isbn);
        hashTitle = hashInit(title);
        hashAuthor = hashInit(author);
        hashYearOfPublication = hashInit(yearOfPublication);
        hashPublisher = hashInit(publisher);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInfo() {
        return isbn + "; " + title + "; " + author + "; " + yearOfPublication + "; " + publisher;
    }

    public ObservableValue<String> isbnProperty() {
        return new SimpleStringProperty(isbn);
    }

    public ObservableValue<String> titleProperty() {
        return new SimpleStringProperty(title);
    }

    public ObservableValue<String> authorProperty() {
        return new SimpleStringProperty(author);
    }

    public ObservableValue<String> yearOfPublicationProperty() {
        return new SimpleStringProperty(yearOfPublication);
    }

    public ObservableValue<String> publisherProperty() {
        return new SimpleStringProperty(publisher);
    }

    public ObservableValue<String> quantityProperty() {
        return new SimpleStringProperty(quantity);
    }
}