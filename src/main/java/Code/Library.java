package Code;

import BookData.Book;
import BookData.BookList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class Library {
    private TableView<Book> tableView;
    private ObservableList<Book> data;
    private FilteredList<Book> filteredData;

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

        TableColumn<Book, String> yearColumn = new TableColumn<>("Publication Year");
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

    // Method to add a book to the TableView
    public void addBookFromData() {
        List<Book> list = new BookList("books.csv").getBookList();
        data.addAll(list);
    }

    public StackPane getLibraryStackPane() {
        // Top AnchorPane for search
        AnchorPane topPane = new AnchorPane();
        topPane.setPrefSize(1024, 110);

        // Create search mode ComboBox and search Label
        ComboBox<String> searchModeComboBox = new ComboBox<>();
        searchModeComboBox.getItems().addAll("Title", "ISBN", "Author", "Publisher", "Publish Date");
        searchModeComboBox.setValue("Title");
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

        topPane.getChildren().addAll(searchModeComboBox, searchField, searchLabel);

        // Set up filtering
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchMode = searchModeComboBox.getValue();
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                switch (searchMode) {
                    case "ISBN":
                        return book.getIsbn().toLowerCase().contains(lowerCaseFilter);
                    case "Author":
                        return book.getAuthor().toLowerCase().contains(lowerCaseFilter);
                    case "Publisher":
                        return book.getPublisher().toLowerCase().contains(lowerCaseFilter);
                    case "Publish Date":
                        return book.getYearOfPublication().toLowerCase().contains(lowerCaseFilter);
                    default:
                        return book.getTitle().toLowerCase().contains(lowerCaseFilter);
                }
            });
        });

        AnchorPane centerPane = new AnchorPane(tableView);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setTop(topPane);

        StackPane pane = new StackPane(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);

        return pane;
    }
}
