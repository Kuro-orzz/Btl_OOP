package UI.Sidebar.BorrowBook;

import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowBook.BorrowedData.BorrowedList;
import Controller.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class showBorrowed {
    private TableView<Borrowed> tableView;
    private ObservableList<Borrowed> data;
    private FilteredList<Borrowed> filteredData;

    /**
     * Show borrowed constructor.
     * @param controller the main controller.
     */
    public showBorrowed(AppController controller) {
        data = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(data);
        tableView = new TableView<>(filteredData);
        tableView.getStylesheets().add(getClass()
                .getResource("/styles/borrowed.css").toExternalForm());
        tableView.getStyleClass().add("table-view");
        tableView.setLayoutX(100);
        tableView.setLayoutY(20);

        //init table columns containing data
        initializeBorrowedTableColumns();

        //get Books' data
        addBookFromData();
    }

    /**
     * Init column of borrowed table.
     */
    private void initializeBorrowedTableColumns() {
        TableColumn<Borrowed, String> idColumn = new TableColumn<>("Id");
        idColumn.getStyleClass().add("id-column");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<Borrowed, String> fullNameColumn = new TableColumn<>("Full name");
        fullNameColumn.getStyleClass().add("fullName-column");
        fullNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());

        TableColumn<Borrowed, String> isbnColumn = new TableColumn<>("Isbn");
        isbnColumn.getStyleClass().add("isbn-column");
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<Borrowed, String> borrowedDateColumn = new TableColumn<>("Borrowed date");
        borrowedDateColumn.getStyleClass().add("borrowedDate-column");
        borrowedDateColumn.setCellValueFactory(cellData -> cellData.getValue().borrowedDateProperty());

        TableColumn<Borrowed, String> dueDateColumn = new TableColumn<>("Due date");
        dueDateColumn.getStyleClass().add("dueDate-column");
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());

        TableColumn<Borrowed, String> statusColumn = new TableColumn<>("Status");
        statusColumn.getStyleClass().add("status-column");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        tableView.getColumns().addAll(idColumn, fullNameColumn, isbnColumn,
                borrowedDateColumn, dueDateColumn, statusColumn);
    }

    /**
     * Get borrowed info from file to data.
     */
    public void addBookFromData() {
        List<Borrowed> list = new BorrowedList("borrowed.csv").getBorrowedList();
        data.addAll(list);
    }

    /**
     * Method create Library GUI.
     * @return Library StackPane
     */
    public StackPane getBorrowedStackPane() {
        // Top AnchorPane for search
        AnchorPane topPane = new AnchorPane();
        topPane.getStyleClass().add("top-pane");

        // Create search mode ComboBox and search Label
        ComboBox<String> searchModeComboBox = new ComboBox<>();
        searchModeComboBox.getItems().addAll("Id", "Full name", "Isbn", "Status");
        searchModeComboBox.setValue("Full name");
        searchModeComboBox.getStylesheets()
                .add(getClass().getResource("/styles/borrowed.css").toExternalForm());
        searchModeComboBox.getStyleClass().add("combo-box");
        searchModeComboBox.setLayoutX(160.0);
        searchModeComboBox.setLayoutY(60.0);

        Label searchLabel = new Label("SEARCH BY:");
        searchLabel.setLayoutX(180.0);
        searchLabel.setLayoutY(32.0);

        TextField searchField = new TextField();
        searchField.getStylesheets().add(getClass()
                .getResource("/styles/borrowed.css").toExternalForm());
        searchField.getStyleClass().add("search-field");
        searchField.setLayoutX(272.0);
        searchField.setLayoutY(34.0);
        searchField.setPrefSize(500, 50);
        searchField.setStyle("-fx-background-color: #ffffff;");
        searchField.setPadding(new Insets(10, 10, 10, 10));

        topPane.getChildren().addAll(searchModeComboBox, searchField, searchLabel);

        // Set up filtering for search bar
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Borrowed> filteredData = FXCollections.observableArrayList();
            String searchMode = searchModeComboBox.getValue();
            BorrowedList borrowedList = new BorrowedList();
            borrowedList.setBorrowedList(FXCollections.observableArrayList(data));
            if (newValue == null || newValue.isEmpty()) {
                filteredData.setAll(borrowedList.getBorrowedList());
            } else {
                List<Borrowed> searchResults = new ArrayList<>();
                switch (searchMode) {
                    case "Id":
                        searchResults = borrowedList.search("Id", newValue);
                        break;
                    case "Isbn":
                        searchResults = borrowedList.search("Isbn", newValue);
                        break;
                    case "Status":
                        searchResults = borrowedList.search("Status", newValue);
                        break;
                    default:
                        searchResults = borrowedList.search("Full name", newValue);
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
}
