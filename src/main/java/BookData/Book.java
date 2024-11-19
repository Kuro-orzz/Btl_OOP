package BookData;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String yearOfPublication;
    private String publisher;
    private String IMG_PATH_SIZE_S;
    private String IMG_PATH_SIZE_M;
    private String IMG_PATH_SIZE_L;

    public Book() {}

    public Book(String isbn, String title, String author, String yearOfPublication, String publisher) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
        this.publisher = publisher;
    }

    public Book(String[] data) {
        this.isbn = data[0];
        this.title = data[1];
        this.author = data[2];
        this.yearOfPublication = data[3];
        this.publisher = data[4];
        this.IMG_PATH_SIZE_S = data[5];
        this.IMG_PATH_SIZE_M = data[6];
        this.IMG_PATH_SIZE_L = data[7];
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
}