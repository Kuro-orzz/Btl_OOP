package UI.Sidebar.Library.ContextMenu;

import UI.Sidebar.Library.API.OpenLibraryAPI;
import UI.Sidebar.Library.BookData.Book;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookDetails {

    /**
     * Show book details to screen.
     * @param book info of this book
     */
    public void showBookDetails(Book book) {
        OpenLibraryAPI api = new OpenLibraryAPI();

        Label loadingLabel = new Label("Loading cover...");
        StackPane img = new StackPane(loadingLabel);

        Task<StackPane> imageTask = api.getImageAsync(book.getIsbn());
        imageTask.setOnSucceeded(event -> {
            StackPane imagePane = imageTask.getValue();
            Platform.runLater(() -> {
                img.getChildren().clear();
                img.getChildren().add(imagePane);
            });
        });
        imageTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                loadingLabel.setText("Failed to load cover.");
            });
        });

        Thread imageThread = new Thread(imageTask);
        imageThread.setDaemon(true);
        imageThread.start();

        Label isbnLabel = new Label("ISBN: " + book.getIsbn());
        isbnLabel.setWrapText(true);
        isbnLabel.setMaxWidth(450);

        Label titleLabel = new Label("Title: " + book.getTitle());
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(450);

        Label authorLabel = new Label("Author: " + book.getAuthor());
        authorLabel.setWrapText(true);
        authorLabel.setMaxWidth(450);

        Label yearLabel = new Label("Year of Publication: " + book.getYearOfPublication());
        yearLabel.setWrapText(true);
        yearLabel.setMaxWidth(450);

        Label publisherLabel = new Label("Publisher: " + book.getPublisher());
        publisherLabel.setWrapText(true);
        publisherLabel.setMaxWidth(450);

        Label quantityLabel = new Label("Quantity: " + book.getQuantity());
        quantityLabel.setWrapText(true);
        quantityLabel.setMaxWidth(450);

        VBox textVBox = new VBox(10);
        textVBox.setPadding(new Insets(30, 30, 30, 30));
        textVBox.getChildren().addAll(isbnLabel, titleLabel, authorLabel, yearLabel, publisherLabel, quantityLabel);

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.getChildren().addAll(img, textVBox);

        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(30, 30, 30, 30));
        vbox.getChildren().add(hbox);

        Stage stage = new Stage();
        stage.setTitle("Book Details");
        Scene scene = new Scene(vbox, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
}
