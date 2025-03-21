package UI.Sidebar.Library.ContextMenu;

import CsvFile.UpdateDataFromListToFile;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.Library.BookData.BookList;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class DeleteBook {
    /**
     * Delete book from data.
     * @param data where book will be deleted
     * @param book object that will be deleted
     */
    public void deleteBook(ObservableList<Book> data, Book book, Account account) {
        if (account.isAdmin()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Book");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this book?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                data.remove(book);
                removeBookFromCSV(book);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Access Denied!");
            alert.setHeaderText(null);
            alert.setContentText("You must be admin to delete this book!");
            alert.show();
        }
    }

    /**
     * Remove book from database.
     * @param book object that will be deleted
     */
    private void removeBookFromCSV(Book book) {
        UpdateDataFromListToFile csvWriter = new UpdateDataFromListToFile();
        List<Book> books = new BookList("books.csv").getBookList();
        books.removeIf(b -> b.getIsbn().equals(book.getIsbn())); // Remove the book
        csvWriter.updateBooks( "books.csv", books);
    }
}
