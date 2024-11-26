package UI.Sidebar.Library;

import UI.Sidebar.Library.BookData.Book;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookDetails {
    /**
     * Show book details to screen.
     * @param book info of this book
     */
    public void showBookDetails(Book book) {
        Label isbnLabel = new Label("ISBN: " + book.getIsbn());
        Label titleLabel = new Label("Title: " + book.getTitle());
        Label authorLabel = new Label("Author: " + book.getAuthor());
        Label yearLabel = new Label("Year of Publication: " + book.getYearOfPublication());
        Label publisherLabel = new Label("Publisher: " + book.getPublisher());
        Label quantityLabel = new Label("Quantity: " + book.getQuantity());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(isbnLabel, titleLabel, authorLabel, yearLabel, publisherLabel, quantityLabel);

        Stage stage = new Stage();
        stage.setTitle("Book Details");
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}
