package UI.Sidebar.Library;

import CsvFile.AppendDataToFile;
import CsvFile.UpdateDataFromListToFile;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.Library.BookData.BookList;
import Controller.AppController;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Library {
    private TableView<Book> tableView;
    private ObservableList<Book> data;
    private FilteredList<Book> filteredData;

    /**
     * Library constructor.
     * @param controller the main controller.
     */
    public Library(AppController controller) {
        data = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(data);
        tableView = new TableView<>(filteredData);
        tableView.getStylesheets().add(getClass()
                .getResource("/styles/library.css").toExternalForm());
        tableView.getStyleClass().add("table-view");
        tableView.setLayoutX(75);
        tableView.setLayoutY(20);

        //init table columns containing data
        initializeBookTableColumns();

        //get Books' data
        addBookFromData();

        //add context menu
        addTableContextMenu();
    }

    /**
     * Method init Book Table Column with Cell data.
     *
     */
    private void initializeBookTableColumns() {
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.getStyleClass().add("isbn-column");
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Title");
        titleColumn.getStyleClass().add("title-column");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.getStyleClass().add("author-column");
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

        TableColumn<Book, String> yearColumn = new TableColumn<>("Year of publication");
        yearColumn.getStyleClass().add("year-column");
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearOfPublicationProperty());

        TableColumn<Book, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.getStyleClass().add("publisher-column");
        publisherColumn.setCellValueFactory(cellData -> cellData.getValue().publisherProperty());

        TableColumn<Book, String> quantity = new TableColumn<>("Quantity");
        quantity.getStyleClass().add("quantity-column");
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn,
                yearColumn, publisherColumn, quantity);
    }

    /**
     * Method to add a book to the TableView.
     *
     */
    public void addBookFromData() {
        List<Book> list = new BookList("books.csv").getBookList();
        data.addAll(list);
    }

    /**
     * Method create Library GUI.
     * @param account current account
     * @return Library StackPane
     */
    public StackPane getLibraryStackPane(Account account) {
        // Top AnchorPane for search
        AnchorPane topPane = new AnchorPane();
        topPane.getStyleClass().add("top-pane");

        // Create search mode ComboBox and search Label
        ComboBox<String> searchModeComboBox = new ComboBox<>();
        searchModeComboBox.getItems().addAll("Title", "ISBN", "Author",
                "Year of publication", "Publisher");
        searchModeComboBox.setValue("Title");
        searchModeComboBox.getStylesheets()
                .add(getClass().getResource("/styles/library.css").toExternalForm());
        searchModeComboBox.getStyleClass().add("combo-box");
        searchModeComboBox.setLayoutX(160.0);
        searchModeComboBox.setLayoutY(60.0);

        Label searchLabel = new Label("SEARCH BY:");
        searchLabel.setLayoutX(180.0);
        searchLabel.setLayoutY(32.0);

        TextField searchField = new TextField();
        searchField.getStylesheets().add(getClass()
                .getResource("/styles/library.css").toExternalForm());
        searchField.getStyleClass().add("search-field");
        searchField.setLayoutX(272.0);
        searchField.setLayoutY(34.0);
        searchField.setPrefSize(500, 50);
        searchField.setStyle("-fx-background-color: #ffffff;");
        searchField.setPadding(new Insets(10, 10, 10, 10));

        Button addButton = new Button("Add Book");
        addButton.getStylesheets().add(getClass()
                .getResource("/styles/library.css").toExternalForm());
        addButton.getStyleClass().add("button");
        addButton.setLayoutX(800.0);
        addButton.setLayoutY(34.0);
        addButton.setOnAction(e -> openAddBookStage());

        topPane.getChildren().addAll(searchModeComboBox, searchField, searchLabel, addButton);

        // Set up filtering
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Book> filteredData = FXCollections.observableArrayList();
            String searchMode = searchModeComboBox.getValue();
            BookList list = new BookList();
            list.setBookList(FXCollections.observableArrayList(data));
            if (newValue == null || newValue.isEmpty()) {
                filteredData.setAll(list.getBookList());
            } else {
                List<Book> searchResults = new ArrayList<>();
                switch (searchMode) {
                    case "ISBN":
                        searchResults = list.search("isbn", newValue);
                        break;
                    case "Author":
                        searchResults = list.search("author", newValue);
                        break;
                    case "Year of publication":
                        searchResults = list.search("yearOfPublication", newValue);
                        break;
                    case "Publisher":
                        searchResults = list.search("publisher", newValue);
                        break;
                    default:
                        searchResults = list.search("title", newValue);
                }
                filteredData.setAll(searchResults);
            }
            tableView.setItems(filteredData);
        });

        AnchorPane centerPane = new AnchorPane(tableView);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setTop(topPane);

        StackPane pane = new StackPane(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);

        return pane;
    }

    /**
     * Pop up Stage to add Book.
     *
     */
    private void openAddBookStage() {
        Stage stage = new Stage();
        stage.setTitle("Add New Account");
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        TextField titledField = new TextField();
        titledField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField yOpField = new TextField();
        yOpField.setPromptText("Year of Publication");
        TextField publisherField = new TextField();
        publisherField.setPromptText("Publisher");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");
        Button doneButton = new Button("Done");
        doneButton.getStylesheets().add(getClass()
                .getResource("/styles/library.css").toExternalForm());
        doneButton.getStyleClass().add("button");
        doneButton.setOnAction(e -> {
            String isbn = isbnField.getText();
            String title = titledField.getText();
            String author = authorField.getText();
            String yOp = yOpField.getText();
            String publisher = publisherField.getText();
            String quantity = quantityField.getText();
            Book newBook = new Book(isbn, title, author, yOp, publisher, quantity);
            appendBookToCSV(newBook);
            data.add(newBook); // Refresh the TableView
            stage.close();
        });
        vbox.getChildren().addAll(isbnField, titledField, authorField, yOpField,
                publisherField, quantityField, doneButton);
        Scene scene = new Scene(vbox, 300, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to append new Book to books's storage file.
     * @param newBook book to be appended
     */
    private void appendBookToCSV(Book newBook) {
        AppendDataToFile csvReader = new AppendDataToFile();
        csvReader.appendBook("books.csv", newBook);
    }

    /**
     * Method to add context menu to table rows.
     */
    private void addTableContextMenu() {
        tableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem viewDetails = new MenuItem("View Details");
            viewDetails.setOnAction(event -> {
                Book selectedBook = row.getItem();
                if (selectedBook != null) {
                    showBookDetails(selectedBook);
                }
            });

            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(event -> {
                Book selectedBook = row.getItem();
                if (selectedBook != null) {
                    openEditBookStage(selectedBook);
                }
            });

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                Book selectedBook = row.getItem();
                if (selectedBook != null) {
                    deleteBook(selectedBook);
                }
            });

            contextMenu.getItems().addAll(viewDetails, editItem, deleteItem);

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });
    }

    private void openEditBookStage(Book book) {
        Stage stage = new Stage();
        stage.setTitle("Edit Book");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        TextField isbnField = new TextField(book.getIsbn());
        isbnField.setPromptText("ISBN");
        TextField titleField = new TextField(book.getTitle());
        titleField.setPromptText("Title");
        TextField authorField = new TextField(book.getAuthor());
        authorField.setPromptText("Author");
        TextField yOpField = new TextField(book.getYearOfPublication());
        yOpField.setPromptText("Year of Publication");
        TextField publisherField = new TextField(book.getPublisher());
        publisherField.setPromptText("Publisher");
        TextField quantityField = new TextField(book.getQuantity());
        quantityField.setPromptText("Quantity");

        Button doneButton = new Button("Done");
        doneButton.getStylesheets().add(getClass().getResource("/styles/library.css").toExternalForm());
        doneButton.getStyleClass().add("button");
        doneButton.setOnAction(e -> {
            book.setIsbn(isbnField.getText());
            book.setTitle(titleField.getText());
            book.setAuthor(authorField.getText());
            book.setYearOfPublication(yOpField.getText());
            book.setPublisher(publisherField.getText());
            book.setQuantity(quantityField.getText());

            updateBookInCSV(book);
            tableView.refresh();
            stage.close();
        });
        vbox.getChildren().addAll(isbnField, titleField, authorField, yOpField, publisherField, quantityField, doneButton);
        Scene scene = new Scene(vbox, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void updateBookInCSV(Book book) {
        // Implement method to update the book information in the CSV file
        UpdateDataFromListToFile csvWriter = new UpdateDataFromListToFile();
        List<Book> books = new BookList("books.csv").getBookList();
        books.removeIf(b -> b.getIsbn().equals(book.getIsbn())); // Remove old book entry
        books.add(book); // Add updated book entry
        csvWriter.updateBooks("books.csv", books);
    }

    private void deleteBook(Book book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Book");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this book?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            data.remove(book);
            removeBookFromCSV(book);
        }
    }

    private void removeBookFromCSV(Book book) {
        UpdateDataFromListToFile csvWriter = new UpdateDataFromListToFile();
        List<Book> books = new BookList("books.csv").getBookList();
        books.removeIf(b -> b.getIsbn().equals(book.getIsbn())); // Remove the book
        csvWriter.updateBooks( "books.csv", books);
    }



    /**
     * Method to show book details in a new stage.
     * @param book the selected book to display details for.
     */
    private void showBookDetails(Book book) {
        Stage stage = new Stage();
        stage.setTitle("Book Details");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label isbnLabel = new Label("ISBN: " + book.getIsbn());
        Label titleLabel = new Label("Title: " + book.getTitle());
        Label authorLabel = new Label("Author: " + book.getAuthor());
        Label yearLabel = new Label("Year of Publication: " + book.getYearOfPublication());
        Label publisherLabel = new Label("Publisher: " + book.getPublisher());
        Label quantityLabel = new Label("Quantity: " + book.getQuantity());

        vbox.getChildren().addAll(isbnLabel, titleLabel, authorLabel, yearLabel, publisherLabel, quantityLabel);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}