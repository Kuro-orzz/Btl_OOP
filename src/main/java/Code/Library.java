package Code;

import Logic.Book;
import Logic.BookList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        filteredData = new FilteredList<>(data, e -> true);
        tableView = new TableView<>(filteredData);
        tableView.setPrefSize(896, 576);
        tableView.setStyle("-fx-border-style: solid; -fx-border-color: #CCCCCC;");
    }

    public StackPane getLibraryStackPane() {
        // Top AnchorPane for search
        AnchorPane topPane = new AnchorPane();
        topPane.setPrefSize(1024, 110);

        TextField searchField = new TextField();
        searchField.setLayoutX(172.0);
        searchField.setLayoutY(34.0);
        searchField.setPrefSize(651, 50);
        searchField.setStyle("-fx-background-color: #ffffff;");
        searchField.setPadding(new Insets(10, 10, 10, 10));

        Label searchLabel = new Label("SEARCH");
        searchLabel.setLayoutX(108.0);
        searchLabel.setLayoutY(55.0);

        topPane.getChildren().addAll(searchField, searchLabel);

        // Define columns with cell value factories
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setPrefWidth(75);
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Title");
        titleColumn.setPrefWidth(456);
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setPrefWidth(110);
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

        TableColumn<Book, String> yearColumn = new TableColumn<>("Publication Year");
        yearColumn.setPrefWidth(115);
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearOfPublicationProperty());

        TableColumn<Book, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setPrefWidth(250);
        publisherColumn.setCellValueFactory(cellData -> cellData.getValue().publisherProperty());

        tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn, yearColumn, publisherColumn);

        addBookFromData();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return book.getTitle().toLowerCase().contains(lowerCaseFilter);
            });
        });

        StackPane centerPane = new StackPane(tableView);
        centerPane.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setTop(topPane);

        StackPane pane = new StackPane(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);

        return pane;
    }

    // Method to add a book to the TableView
    public void addBookFromData() {
        List<Book> list = new BookList("books.csv").getBookList();
        data.addAll(list);
    }
}
