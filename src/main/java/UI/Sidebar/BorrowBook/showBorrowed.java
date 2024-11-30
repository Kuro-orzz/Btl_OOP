package UI.Sidebar.BorrowBook;

import UI.Method;
import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowBook.BorrowedData.BorrowedList;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class showBorrowed extends Method<Borrowed> {
    private final TableView<Borrowed> tableView;
    private final ObservableList<Borrowed> data = FXCollections.observableArrayList();
    private final Account curAcc;

    /**
     * Show borrowed constructor.
     */
    public showBorrowed(Account account) {
        this.curAcc = account;
        // table view
        tableView = new TableView<>(data);
        setTableView(tableView);
        initBorrowedTable();

        // add data
        addBorrowedFromData();
    }

    private TableColumn<Borrowed, String> initColumn(String columnName, String urlCss) {
        TableColumn<Borrowed, String> newColumn = new TableColumn<>(columnName);
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
            case "Borrowed date" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().borrowedDateProperty()
            );
            case "Due date" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().dueDateProperty()
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

    private void initBorrowedTable() {
        tableView.getColumns().addAll(
                initColumn("Id", "id-column"),
                initColumn("Full name", "fullName-column"),
                initColumn("Isbn", "isbn-column"),
                initColumn("Borrowed date", "borrowedDate-column"),
                initColumn("Due date", "dueDate-column"),
                initColumn("Status", "status-column")
        );
    }

    /**
     * Get borrowed info from file to data.
     */
    public void addBorrowedFromData() {
        List<Borrowed> list = new BorrowedList("borrowed.csv").getBorrowedList();
        if (!curAcc.isAdmin()) {
            List<Borrowed> userBorrowedList = new ArrayList<>();
            for (Borrowed borrowed : list) {
                if (borrowed.getFullName().equals(curAcc.getInfo().getFullName())) {
                    userBorrowedList.add(borrowed);
                }
            }
            data.addAll(userBorrowedList);
        } else {
            data.addAll(list);
        }
    }


    /**
     * Method create Library GUI.
     * @return Library StackPane
     */
    public StackPane getBorrowedStackPane(Account account) {
        AnchorPane topPane = new AnchorPane();
        topPane.getStyleClass().add("top-pane");

        String[] adminSearchType = {"Id", "Full name", "Isbn", "Status"};
        String[] userSearchType = {"Id", "Full name", "Isbn"};
        ComboBox<String> searchModeComboBox;
        if (account.isAdmin()) {
            searchModeComboBox = initSearchBox(adminSearchType,
                    "/styles/borrowed.css", "combo-box");
        } else {
            searchModeComboBox = initSearchBox(userSearchType,
                    "/styles/borrowed.css", "combo-box");
        }
        Label searchLabel = initsearchLabel();
        TextField searchField = initSearchField("/styles/borrowed.css", "search-field");

        // add search filter
        addSearchFilter(searchField, searchModeComboBox);

        topPane.getChildren().addAll(searchModeComboBox, searchField, searchLabel);

        return initStackPane(topPane, tableView);
    }

    private void addSearchFilter(TextField searchField, ComboBox<String> searchModeComboBox) {
        if (curAcc.isAdmin()) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                ObservableList<Borrowed> filteredData = FXCollections.observableArrayList();
                String searchMode = searchModeComboBox.getValue();
                BorrowedList borrowedList = new BorrowedList();
                borrowedList.setBorrowedList(FXCollections.observableArrayList(data));
                if (newValue == null || newValue.isEmpty()) {
                    filteredData.setAll(borrowedList.getBorrowedList());
                } else {
                    List<Borrowed> searchResults = switch (searchMode) {
                        case "Id" -> borrowedList.search("Id", newValue);
                        case "Isbn" -> borrowedList.search("Isbn", newValue);
                        case "Status" -> borrowedList.search("Status", newValue);
                        default -> borrowedList.search("Full name", newValue);
                    };
                    filteredData.setAll(searchResults);
                }
                tableView.setItems(filteredData);
            });
        } else {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                ObservableList<Borrowed> filteredData = FXCollections.observableArrayList();
                String searchMode = searchModeComboBox.getValue();
                BorrowedList borrowedList = new BorrowedList();
                borrowedList.setBorrowedList(FXCollections.observableArrayList(data));
                if (newValue == null || newValue.isEmpty()) {
                    filteredData.setAll(borrowedList.getBorrowedList());
                } else {
                    List<Borrowed> searchResults = switch (searchMode) {
                        case "Id" -> borrowedList.search("Id", newValue);
                        case "Status" -> borrowedList.search("Status", newValue);
                        default -> borrowedList.search("Isbn", newValue);
                    };
                    filteredData.setAll(searchResults);
                }
                tableView.setItems(filteredData);
            });
        }
    }
}
