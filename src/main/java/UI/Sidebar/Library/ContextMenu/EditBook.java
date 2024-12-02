package UI.Sidebar.Library.ContextMenu;

import CsvFile.UpdateDataFromListToFile;
import UI.Method;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.Library.BookData.BookList;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    public EditBook(Book book) {
        isbnField.setText(book.getIsbn());
        isbnField.setPromptText("Isbn");
        titleField.setText(book.getTitle());
        titleField.setPromptText("Title");
        authorField.setText(book.getAuthor());
        authorField.setPromptText("Author");
        yearField.setText(book.getYearOfPublication().toString());
        yearField.setPromptText("Year");
        publisherField.setText(book.getPublisher());
        publisherField.setPromptText("Publisher");
        quantityField.setText(book.getQuantity().toString());
        quantityField.setPromptText("Quantity");
        doneButton = new Button("Done");
        applyStylesAndClasses(doneButton, "/styles/library.css", "button");
    }

    public void setButtonAction(Stage stage, ObservableList<Book> data, Book book) {
        doneButton.setOnAction(e -> {
            try {
                String newIsbn = isbnField.getText();
                String newTitle = titleField.getText();
                String newAuthor = authorField.getText();
                String newYearOfPublication = yearField.getText();
                String newPublisher = publisherField.getText();
                String newQuantity = quantityField.getText();
                if (!newIsbn.matches("\\d{10}")) {
                    throw new IllegalArgumentException("ISBN must be exactly 10 digits.");
                }
                int currentYear = java.time.Year.now().getValue();
                int year = Integer.parseInt(newYearOfPublication);
                if (year > currentYear) {
                    throw new IllegalArgumentException("Year of publication must not exceed the current year.");
                }
                int qty = Integer.parseInt(newQuantity);
                if (qty < 0) {
                    throw new IllegalArgumentException("Quantity must be greater than or equal to 0.");
                }
                book.setIsbn(newIsbn);
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setYearOfPublication(newYearOfPublication);
                book.setPublisher(newPublisher);
                book.setQuantity(newQuantity);
                updateBookInCSV(data, book, book.getIsbn());
                stage.close();
            } catch (NumberFormatException ex) {
                showAlert("Year and Quantity must be numeric.");
            } catch (IllegalArgumentException ex) {
                showAlert(ex.getMessage());
            }
        });
    }

    public void displayEditBook(ObservableList<Book> data, Book book, Account account) {
        if (account.isAdmin()) {
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Denied!");
            alert.setHeaderText(null);
            alert.setContentText("You must be admin to edit this book!");
            alert.show();
        }
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
