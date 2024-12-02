package UI.Sidebar.BorrowBook;

import UI.Method;
import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowBook.BorrowedData.BorrowedList;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
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

        // Add context menu for admin
        if (curAcc.isAdmin()) {
            addContextMenu();
        }
    }

    /**
     * Initialize new column for table view.
     * @param columnName name of column
     * @param urlCss url to css
     * @return column of table view
     */
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

    /**
     * Init new table view.
     */
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
     * Add context menu for admin with the "Return" function.
     */
    private void addContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem returnItem = new MenuItem("Return");
        returnItem.setOnAction(event -> {
            Borrowed selectedBorrowed = tableView.getSelectionModel().getSelectedItem();
            if (selectedBorrowed != null && "Borrowing".equals(selectedBorrowed.getStatus())) {
                selectedBorrowed.setStatus("Returned");

                // Update the CSV file for borrowed books
                new UpdateDataFromListToFile().updateBorrowed("borrowed.csv", new ArrayList<>(data));

                // Increment the quantity of the book in books.csv
                incrementBookQuantity(selectedBorrowed.getIsbn());

                // Refresh the table to show updated status
                tableView.refresh();

                showAlert("Success", "The book has been returned!");
            } else {
                showAlert("Invalid Operation", "Only books with the status 'Borrowing' can be returned!");
            }
        });

        contextMenu.getItems().add(returnItem);

        // Set context menu on table rows
        tableView.setRowFactory(tv -> {
            TableRow<Borrowed> row = new TableRow<>();
            row.setOnContextMenuRequested((ContextMenuEvent event) -> {
                if (!row.isEmpty()) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    /**
     * Increase the book quantity by 1 when return.
     * @param isbn returned book isbn
     */
    private void incrementBookQuantity(String isbn) {
        GetDataFromFile dataFetcher = new GetDataFromFile();
        UpdateDataFromListToFile updater = new UpdateDataFromListToFile();
        List<Book> books = dataFetcher.getBooksFromFile("books.csv");

        if (books != null) {
            for (Book book : books) {
                if (book.getIsbn().equals(isbn)) {
                    int currentQuantity = Integer.parseInt(book.getQuantity());
                    book.setQuantity(String.valueOf(currentQuantity + 1));
                    break;
                }
            }
            updater.updateBooks("books.csv", books);
        } else {
            showAlert("Error", "Failed to load books data for updating quantity.");
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
    }

    /**
     * Helper to show alerts.
     * @param title Alert title.
     * @param content Alert content.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
