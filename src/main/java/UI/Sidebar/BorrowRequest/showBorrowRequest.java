package UI.Sidebar.BorrowRequest;

import Controller.AppController;
import CsvFile.AppendDataToFile;
import CsvFile.UpdateDataFromListToFile;
import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequest;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequestList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class showBorrowRequest {
    private List<BorrowRequest> list = new BorrowRequestList("borrowRequest.csv").getBorrowRequestList();
    private TableView<BorrowRequest> tableView;
    private ObservableList<BorrowRequest> data;
    private FilteredList<BorrowRequest> filteredData;

    /**
     * Show borrow request constructor.
     * @param controller the main controller.
     */
    public showBorrowRequest(AppController controller) {
        data = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(data);
        tableView = new TableView<>(filteredData);
        tableView.getStylesheets().add(getClass()
                .getResource("/styles/borrowRequest.css").toExternalForm());
        tableView.getStyleClass().add("table-view");
        tableView.setLayoutX(100);
        tableView.setLayoutY(20);

        initializeBorrowRequestTableColumns();

        addBookFromData();
    }

    /**
     * Init column of borrow request table.
     */
    private void initializeBorrowRequestTableColumns() {
        TableColumn<BorrowRequest, String> idColumn = new TableColumn<>("Id");
        idColumn.getStyleClass().add("id-column");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<BorrowRequest, String> fullNameColumn = new TableColumn<>("Full name");
        fullNameColumn.getStyleClass().add("fullName-column");
        fullNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());

        TableColumn<BorrowRequest, String> isbnColumn = new TableColumn<>("Isbn");
        isbnColumn.getStyleClass().add("isbn-column");
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<BorrowRequest, String> requestDateColumn = new TableColumn<>("Request date");
        requestDateColumn.getStyleClass().add("requestDate-column");
        requestDateColumn.setCellValueFactory(cellData -> cellData.getValue().requestDateProperty());

        TableColumn<BorrowRequest, String> statusColumn = new TableColumn<>("Status");
        statusColumn.getStyleClass().add("status-column");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        TableColumn<BorrowRequest, Boolean> acceptColumn = new TableColumn<>("Accept");
        acceptColumn.getStyleClass().add("accept-column");
        acceptColumn.setCellValueFactory(cellData -> cellData.getValue().acceptProperty());
        acceptColumn.setCellFactory(CheckBoxTableCell.forTableColumn(acceptColumn));
        acceptColumn.setEditable(true);

        TableColumn<BorrowRequest, Boolean> declineColumn = new TableColumn<>("Decline");
        declineColumn.getStyleClass().add("decline-column");
        declineColumn.setCellValueFactory(cellData -> cellData.getValue().declineProperty());
        declineColumn.setCellFactory(CheckBoxTableCell.forTableColumn(declineColumn));
        declineColumn.setEditable(true);

        tableView.getColumns().addAll(idColumn, fullNameColumn, isbnColumn,
                requestDateColumn, statusColumn, acceptColumn, declineColumn);

        tableView.setEditable(true);

        // add listener(allow only one check at the moment) to all new borrow request added to table
        tableView.getItems().addListener((ListChangeListener<BorrowRequest>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (BorrowRequest request : change.getAddedSubList()) {
                        addListenersToBorrowRequest(request);
                    }
                }
            }
        });
    }

    /**
     * Get borrowed info from file to data.
     */
    public void addBookFromData() {
        data.addAll(list);
    }

    public Button applyButton() {
        Button applyButton = new Button("Apply");
        applyButton.getStyleClass().add("apply-button");
        applyButton.setLayoutX(800.0);
        applyButton.setLayoutY(34.0);
        applyButton.setOnAction(event -> {
            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i).getAccept() && !data.get(i).getDecline()) {
                    continue;
                }
                BorrowRequest request = data.get(i);
                Borrowed borrowed = new Borrowed(request.getId(), request.getFullName(),
                        request.getIsbn(), request.getRequestDate(), "Borrowing");
                list.remove(request);
                UpdateDataFromListToFile newData = new UpdateDataFromListToFile();
                newData.updateBorrowRequest("borrowRequest.csv", list);
                if (data.get(i).getAccept()) {
                    AppendDataToFile newRequest = new AppendDataToFile();
                    newRequest.appendBorrowed("borrowed.csv", borrowed);
                }
                data.remove(request);
            }
        });
        return applyButton;
    }

    /**
     * Add listener to one borrow request
     * @param borrowRequest borrow request that will be added listener
     */
    public void addListenersToBorrowRequest(BorrowRequest borrowRequest) {
        borrowRequest.acceptProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                borrowRequest.setDecline(false);
            }
        });
        borrowRequest.declineProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                borrowRequest.setAccept(false);
            }
        });
    }

    /**
     * Method create Library GUI.
     * @return Library StackPane
     */
    public StackPane getBorrowRequestStackPane() {
        // Top AnchorPane for search
        AnchorPane topPane = new AnchorPane();
        topPane.getStyleClass().add("top-pane");

        // Create search mode ComboBox and search Label
        ComboBox<String> searchModeComboBox = new ComboBox<>();
        searchModeComboBox.getItems().addAll("Id", "Full name", "Isbn", "Request date", "Status");
        searchModeComboBox.setValue("Full name");
        searchModeComboBox.getStylesheets()
                .add(getClass().getResource("/styles/borrowRequest.css").toExternalForm());
        searchModeComboBox.getStyleClass().add("combo-box");
        searchModeComboBox.setLayoutX(160.0);
        searchModeComboBox.setLayoutY(60.0);

        Label searchLabel = new Label("SEARCH BY:");
        searchLabel.setLayoutX(180.0);
        searchLabel.setLayoutY(32.0);

        TextField searchField = new TextField();
        searchField.getStylesheets().add(getClass()
                .getResource("/styles/borrowRequest.css").toExternalForm());
        searchField.getStyleClass().add("search-field");
        searchField.setLayoutX(272.0);
        searchField.setLayoutY(34.0);
        searchField.setPrefSize(500, 50);
        searchField.setStyle("-fx-background-color: #ffffff;");
        searchField.setPadding(new Insets(10, 10, 10, 10));

        topPane.getChildren().addAll(searchModeComboBox, searchField, searchLabel, applyButton());

        // Set up filtering for search bar
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<BorrowRequest> filteredData = FXCollections.observableArrayList();
            String searchMode = searchModeComboBox.getValue();
            BorrowRequestList borrowRequestList = new BorrowRequestList();
            borrowRequestList.setBorrowRequestList(FXCollections.observableArrayList(data));
            if (newValue == null || newValue.isEmpty()) {
                filteredData.setAll(borrowRequestList.getBorrowRequestList());
            } else {
                List<BorrowRequest> searchResults = new ArrayList<>();
                switch (searchMode) {
                    case "Id":
                        searchResults = borrowRequestList.search("Id", newValue);
                        break;
                    case "Isbn":
                        searchResults = borrowRequestList.search("Isbn", newValue);
                        break;
                    case "Request date":
                        searchResults = borrowRequestList.search("Request date", newValue);
                        break;
                    case "Status":
                        searchResults = borrowRequestList.search("Status", newValue);
                        break;
                    default:
                        searchResults = borrowRequestList.search("Full name", newValue);
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
