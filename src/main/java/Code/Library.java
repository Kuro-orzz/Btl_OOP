package Code;

import BookData.Book;
import BookData.BookList;
import Logic.CsvReader;
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
        tableView.setPrefSize(840, 576);
        tableView.setStyle("-fx-border-style: solid; -fx-border-color: #CCCCCC;");
        tableView.setLayoutX(75);
        tableView.setLayoutY(100);

        //init table columns containing data
        initializeBookTableColumns();

        //get Books' data
        addBookFromData();
    }

    /**
     * Method init Book Table Column with Cell data.
     *
     */
    private void initializeBookTableColumns() {
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setPrefWidth(75);
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Title");
        titleColumn.setPrefWidth(345);
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setPrefWidth(110);
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

        TableColumn<Book, String> yearColumn = new TableColumn<>("Year of publication");
        yearColumn.setPrefWidth(115);
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearOfPublicationProperty());

        TableColumn<Book, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setPrefWidth(140);
        publisherColumn.setCellValueFactory(cellData -> cellData.getValue().publisherProperty());

        TableColumn<Book, String> quantity = new TableColumn<>("Quantity");
        quantity.setPrefWidth(111);
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

        tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn, yearColumn, publisherColumn, quantity);
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
     * @return Library StackPane
     */
    public StackPane getLibraryStackPane() {
        // Top AnchorPane for search
        AnchorPane topPane = new AnchorPane();
        topPane.setPrefSize(1024, 110);

        // Create search mode ComboBox and search Label
        ComboBox<String> searchModeComboBox = new ComboBox<>();
        searchModeComboBox.getItems().addAll("Title", "ISBN", "Author", "Year of publication", "Publisher");
        searchModeComboBox.setValue("Title");
        searchModeComboBox.getStylesheets().add(getClass().getResource("/styles/Lib&User.css").toExternalForm());
        searchModeComboBox.getStyleClass().add("combo-box");
        searchModeComboBox.setLayoutX(160.0);
        searchModeComboBox.setLayoutY(60.0);
        searchModeComboBox.setPrefSize(104, 25);

        Label searchLabel = new Label("SEARCH BY:");
        searchLabel.setLayoutX(180.0);
        searchLabel.setLayoutY(32.0);

        TextField searchField = new TextField();
        searchField.setLayoutX(272.0);
        searchField.setLayoutY(34.0);
        searchField.setPrefSize(500, 50);
        searchField.setStyle("-fx-background-color: #ffffff;");
        searchField.setPadding(new Insets(10, 10, 10, 10));

        Button addButton = new Button("Add Book");
        addButton.getStylesheets().add(getClass().getResource("/styles/Lib&User.css").toExternalForm());
        addButton.getStyleClass().add("button");
        addButton.setLayoutX(800.0);
        addButton.setLayoutY(34.0);
        addButton.setOnAction(e -> openAddBookStage());

        topPane.getChildren().addAll(searchModeComboBox, searchField, searchLabel, addButton);

        // Set up filtering
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Book> filteredData = FXCollections.observableArrayList();
            String searchMode = searchModeComboBox.getValue();
            BookList list = new BookList("books.csv");
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
        vbox.setPadding(new Insets(20, 20, 20, 20));
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
        doneButton.getStylesheets().add(getClass().getResource("/styles/Lib&User.css").toExternalForm());
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
        vbox.getChildren().addAll(isbnField, titledField, authorField, yOpField, publisherField, quantityField, doneButton);
        Scene scene = new Scene(vbox, 300, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to append new Book to books's storage file.
     * @param newBook book to be appended
     */
    private void appendBookToCSV(Book newBook) {
        CsvReader csvReader = new CsvReader();
        csvReader.appendBookToFile(newBook, "books.csv");
    }
}
