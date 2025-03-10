package UI.Sidebar.BorrowRequest;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;
import UI.Method;
import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequest;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequestList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class showBorrowRequest extends Method<BorrowRequest> {
    private final List<BorrowRequest> list = new BorrowRequestList("borrowRequests.csv").getBorrowRequestList();
    private final TableView<BorrowRequest> tableView;
    private final ObservableList<BorrowRequest> data = FXCollections.observableArrayList();

    /**
     * Show borrow request constructor.
     */
    public showBorrowRequest() {
        // table view
        tableView = new TableView<>(data);
        setTableView(tableView);
        initBorrowRequestTable();

        // add data
        data.addAll(list);
    }

    /**
     * Initialize new column for table view.
     * @param columnName name of column
     * @param urlCss url to css
     * @return column of table view
     */
    private TableColumn<BorrowRequest, String> initColumn(String columnName, String urlCss) {
        TableColumn<BorrowRequest, String> newColumn = new TableColumn<>(columnName);
        newColumn.getStyleClass().add(urlCss);
        switch (columnName) {
            case "Id" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().idProperty()
            );
            case "Full name" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().fullNameProperty()
            );
            case "Isbn" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().isbnProperty()
            );
            case "Request date" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().requestDateProperty()
            );
            case "Status" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().statusProperty()
            );
            default -> {
                System.out.println("Error when init column: " + columnName);
                return null;
            }
        }
        return newColumn;
    }

    /**
     * Initialize checkbox column.
     * @param columnName name of column
     * @param urlCss url to css
     * @return checkbox column
     */
    private TableColumn<BorrowRequest, Boolean> initCheckBoxColumn(String columnName, String urlCss) {
        TableColumn<BorrowRequest, Boolean> newColumn = new TableColumn<>(columnName);
        newColumn.getStyleClass().add(urlCss);
        if (columnName.equals("Accept")){
            newColumn.setCellValueFactory(cellData -> cellData.getValue().acceptProperty());
            newColumn.setCellFactory(CheckBoxTableCell.forTableColumn(newColumn));
            newColumn.setEditable(true);
        } else if (columnName.equals("Decline")){
            newColumn.setCellValueFactory(cellData -> cellData.getValue().declineProperty());
            newColumn.setCellFactory(CheckBoxTableCell.forTableColumn(newColumn));
            newColumn.setEditable(true);
        } else {
            System.out.println("Error when init column: " + columnName);
            return null;
        }
        return newColumn;
    }

    /**
     * Init new table view and set action when press checkbox.
     */
    private void initBorrowRequestTable() {
        tableView.getColumns().addAll(
                initColumn("Id", "id-column"),
                initColumn("Full name", "fullName-column"),
                initColumn("Isbn", "isbn-column"),
                initColumn("Request date", "requestDate-column"),
                initColumn("Status", "status-column"),
                initCheckBoxColumn("Accept", "accept-column"),
                initCheckBoxColumn("Decline", "decline-column")
        );
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
     * Button apply status accept or decline for borrow request.
     * @return new button
     */
    public Button applyButton() {
        Button applyButton = initButton("Apply", "/styles/borrowRequest.css", "apply-button");
        applyButton.setOnAction(event -> {
            UpdateDataFromListToFile updateData = new UpdateDataFromListToFile();
            GetDataFromFile dataFetcher = new GetDataFromFile();
            String booksFileName = "books.csv";

            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i).getAccept() && !data.get(i).getDecline()) {
                    continue;
                }
                BorrowRequest request = data.get(i);
                Borrowed borrowed = new Borrowed(request.getId(), request.getFullName(),
                        request.getIsbn(), request.getRequestDate(), "Borrowing");

                list.remove(request);
                i--;
                updateData.updateBorrowRequest("borrowRequests.csv", list);

                if (request.getAccept()) {
                    updateData.updateBookQuantityWithGetData(booksFileName, request.getIsbn(), dataFetcher);
                } else if (request.getDecline()) {
                    borrowed.setStatus("Declined");
                }

                AppendDataToFile newRequest = new AppendDataToFile();
                newRequest.appendBorrowed("borrowed.csv", borrowed);
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

        String[] searchType = {"Id", "Full name", "Isbn", "Request date", "Status"};
        ComboBox<String> searchModeComboBox = initSearchBox(searchType,
                "/styles/borrowRequest.css", "combo-box");
        Label searchLabel =initsearchLabel();
        TextField searchField = initSearchField("/styles/borrowRequest.css", "search-field");

        // add search filter
        addSearchFilter(searchField, searchModeComboBox);

        topPane.getChildren().addAll(searchModeComboBox, searchField, searchLabel, applyButton());

        return initStackPane(topPane, tableView);
    }

    /**
     * Add search filter for search field
     * @param searchField search
     * @param searchModeComboBox search mode
     */
    private void addSearchFilter(TextField searchField, ComboBox<String> searchModeComboBox) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<BorrowRequest> filteredData = FXCollections.observableArrayList();
            String searchMode = searchModeComboBox.getValue();
            BorrowRequestList borrowRequestList = new BorrowRequestList();
            borrowRequestList.setBorrowRequestList(FXCollections.observableArrayList(data));
            if (newValue == null || newValue.isEmpty()) {
                filteredData.setAll(borrowRequestList.getBorrowRequestList());
            } else {
                List<BorrowRequest> searchResults = switch (searchMode) {
                    case "Id" -> borrowRequestList.search("Id", newValue);
                    case "Isbn" -> borrowRequestList.search("Isbn", newValue);
                    case "Request date" -> borrowRequestList.search("Request date", newValue);
                    case "Status" -> borrowRequestList.search("Status", newValue);
                    default -> borrowRequestList.search("Full name", newValue);
                };
                filteredData.setAll(searchResults);
            }
            tableView.setItems(filteredData);
        });
    }
}
