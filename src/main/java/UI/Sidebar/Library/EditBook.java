package UI.Sidebar.Library;

import CsvFile.UpdateDataFromListToFile;
import UI.Method;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.Library.BookData.BookList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class EditBook extends Method<Book> {
    private final TextField isbnField = new TextField();
    private final TextField titleField =  new TextField();
    private final TextField authorField = new TextField();
    private final TextField yearField = new TextField();
    private final TextField publisherField = new TextField();
    private final TextField quantityField = new TextField();
    private final Button doneButton;

    public EditBook() {
        isbnField.setPromptText("Isbn");
        titleField.setPromptText("Title");
        authorField.setPromptText("Author");
        yearField.setPromptText("Year");
        publisherField.setPromptText("Publisher");
        quantityField.setPromptText("Quantity");
        doneButton = new Button("Done");
        applyStylesAndClasses(doneButton, "/styles/library.css", "button");
    }

    public void setButtonAction(Stage stage, ObservableList<Book> data, Book book) {
        doneButton.setOnAction(e -> {
            String isbn = book.getIsbn();
            book.setIsbn(isbnField.getText());
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setYearOfPublication(yearField.getText());
            book.setPublisher(publisherField.getText());
            book.setQuantity(quantityField.getText());
            updateBookInCSV(data, book, isbn);
//            tableView.refresh();
            stage.close();
        });
    }

    public void displayEditBook(ObservableList<Book> data, Book book) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(isbnField, titleField, authorField,
                yearField, publisherField, quantityField, doneButton);
        Stage stage = new Stage();
        stage.setTitle("Edit Book");
        setButtonAction(stage, data, book);
        Scene scene = new Scene(vbox, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void updateBookInCSV(ObservableList<Book> data, Book book, String isbn) {
        UpdateDataFromListToFile csvWriter = new UpdateDataFromListToFile();
        List<Book> bookList = new BookList("books.csv").getBookList();
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getIsbn().equals(isbn)) {
                bookList.set(i, book);
                break;
            }
        }
        csvWriter.updateBooks("books.csv", bookList);
        data.addAll(bookList);
    }
}
