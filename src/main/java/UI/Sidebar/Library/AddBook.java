package UI.Sidebar.Library;

import CsvFile.AppendDataToFile;
import UI.Method;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddBook extends Method<Book> {
    private final TextField isbnField = new TextField();
    private final TextField titleField =  new TextField();
    private final TextField authorField = new TextField();
    private final TextField yearField = new TextField();
    private final TextField publisherField = new TextField();
    private final TextField quantityField = new TextField();
    private final Button doneButton;

    /**
     * Add book constructor.
     */
    public AddBook() {
        isbnField.setPromptText("Isbn");
        titleField.setPromptText("Title");
        authorField.setPromptText("Author");
        yearField.setPromptText("Year");
        publisherField.setPromptText("Publisher");
        quantityField.setPromptText("Quantity");
        doneButton = new Button("Done");
        applyStylesAndClasses(doneButton, "/styles/library.css", "button");
    }

    /**
     * Set action for button add book.
     * @param stage show to screen
     * @param data list of books
     * @param account current account
     */
    public void setButtonAction(Stage stage, ObservableList<Book> data, Account account) {
        doneButton.setOnAction(e -> {
            String isbn = isbnField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String yOp = yearField.getText();
            String publisher = publisherField.getText();
            String quantity = quantityField.getText();
            Book newBook = new Book(isbn, title, author, yOp, publisher, quantity);
            appendBookToCSV(newBook);
            data.add(newBook);
            stage.close();
        });
    }

    /**
     * Show add book to screen.
     * @param data list of books
     * @param account current account
     */
    public void displayAddBook(ObservableList<Book> data, Account account) {
        if (account.isAdmin()) {
            VBox vbox = new VBox(10);
            vbox.getStyleClass().add("vbox");
            vbox.getChildren().addAll(isbnField, titleField, authorField,
                    yearField, publisherField, quantityField, doneButton);
            Scene scene = new Scene(vbox, 300, 400);
            Stage stage = new Stage();
            stage.setTitle("Add new book");
            setButtonAction(stage, data, account);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Denied!");
            alert.setHeaderText(null);
            alert.setContentText("You must be admin to add a book!");
            alert.show();
        }
    }

    /**
     * Add new book to end of database.
     * @param newBook new book will be added
     */
    public void appendBookToCSV(Book newBook) {
        AppendDataToFile csvReader = new AppendDataToFile();
        csvReader.appendBook("books.csv", newBook);
    }
}
