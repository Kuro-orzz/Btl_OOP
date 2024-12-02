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
        doneButton.setTranslateX(130);
        applyStylesAndClasses(doneButton, "/styles/library.css", "button");
    }

    /**
     * Set action for button add book.
     * @param stage show to screen
     * @param data list of books
     */
    public void setButtonAction(Stage stage, ObservableList<Book> data) {
        doneButton.setOnAction(e -> {
            try {
                String isbn = isbnField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                String yOp = yearField.getText();
                String publisher = publisherField.getText();
                String quantity = quantityField.getText();

                if (!isbn.matches("\\d{10}")) {
                    throw new IllegalArgumentException("ISBN must be exactly 10 digits.");
                }

                for (Book book : data) {
                    if (book.getIsbn().equals(isbn)) {
                        throw new IllegalArgumentException("ISBN already exists in the library.");
                    }
                }

                int currentYear = java.time.Year.now().getValue();
                int year = Integer.parseInt(yOp);
                if (year > currentYear) {
                    throw new IllegalArgumentException("Year of publication must not exceed the current year.");
                }

                int qty = Integer.parseInt(quantity);
                if (qty < 0) {
                    throw new IllegalArgumentException("Quantity must be greater than or equal to 0.");
                }

                Book newBook = new Book(isbn, title, author, yOp, publisher, String.valueOf(qty));
                appendBookToCSV(newBook);
                data.add(newBook);
                stage.close();
        } catch (NumberFormatException ex) {
            showAlert("Year and Quantity must be numeric.");
        } catch (IllegalArgumentException ex){
            showAlert(ex.getMessage());
        }
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
            setButtonAction(stage, data);
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
