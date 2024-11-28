package UI.Sidebar.Library;

import UI.Method;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.Library.BookData.BookList;
import Controller.AppController;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class Library extends Method<Book> {
    private final TableView<Book> tableView;
    private final ObservableList<Book> data = FXCollections.observableArrayList();

    /**
     * Library constructor.
     * @param controller the main controller.
     */
    public Library(AppController controller, Account account) {
        // table view
        tableView = new TableView<>(data);
        setTableView(tableView);
        initBookTable();

        // add data
        List<Book> bookList = new BookList("books.csv").getBookList();
        data.addAll(bookList);

        // context menu
        addTableContextMenu(account);
    }

    /**
     * Initialize new column for table view.
     * @param columnName name of column
     * @param urlCss path that lead to css of that column
     * @return column of table view
     */
    private TableColumn<Book, String> initColumn(String columnName, String urlCss) {
        TableColumn<Book, String> newColumn = new TableColumn<>(columnName);
        newColumn.getStyleClass().add(urlCss);
        switch (columnName) {
            case "Isbn" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().isbnProperty()
            );
            case "Title" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().titleProperty()
            );
            case "Author" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().authorProperty()
            );
            case "Year" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().yearOfPublicationProperty()
            );
            case "Publisher" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().publisherProperty()
            );
            case "Quantity" -> newColumn.setCellValueFactory(
                    cellData -> cellData.getValue().quantityProperty()
            );
            default -> {
                System.out.println("Error when init column: " + columnName);
                return null;
            }
        }
        return newColumn;
    }

    /**
     * Initialize table view to show book in screen.
     */
    private void initBookTable() {
        tableView.getColumns().addAll(
                initColumn("Isbn", "isbn-column"),
                initColumn("Title", "title-column"),
                initColumn("Author", "author-column"),
                initColumn("Year", "year-column"),
                initColumn("Publisher", "publisher"),
                initColumn("Quantity", "quantity-column")
        );
    }

    /**
     * Method create Library GUI.
     * @param account current account
     * @return Library StackPane
     */
    public StackPane getLibraryStackPane(Account account) {
        AnchorPane topPane = new AnchorPane();
        topPane.getStyleClass().add("top-pane");

        // Search bar
        String[] searchType = {"Title", "Isbn", "Author", "Year", "Publisher"};
        ComboBox<String> searchBar = initSearchBox(searchType);
        Label searchLabel = initsearchLabel();
        TextField searchField = initSearchField();

        // Button add book
        Button addButton = initButton("Add book");
        addButton.setOnAction(e -> new AddBook().displayAddBook(data, account));

        // Search filter
        addSearchFilter(searchField, searchBar);

        topPane.getChildren().addAll(searchBar, searchField, searchLabel, addButton);

        return initStackPane(topPane, tableView);
    }

    /**
     * Search filter for search bar.
     * @param searchField text field where we type to find book
     * @param searchBar choose type of search
     */
    private void addSearchFilter(TextField searchField, ComboBox<String> searchBar) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Book> filteredData = FXCollections.observableArrayList();
            String searchMode = searchBar.getValue();
            BookList list = new BookList();
            list.setBookList(FXCollections.observableArrayList(data));
            if (newValue == null || newValue.isEmpty()) {
                filteredData.setAll(list.getBookList());
            } else {
                List<Book> searchResults = switch (searchMode) {
                    case "Isbn" -> list.search("isbn", newValue);
                    case "Author" -> list.search("author", newValue);
                    case "Year" -> list.search("yearOfPublication", newValue);
                    case "Publisher" -> list.search("publisher", newValue);
                    default -> list.search("title", newValue);
                };
                filteredData.setAll(searchResults);
            }
            tableView.setItems(filteredData);
        });
    }

    /**
     * Method to add context menu to table rows.
     */
    private void addTableContextMenu(Account account) {
        tableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem viewDetails = new MenuItem("View Details");
            viewDetails.setOnAction(event -> {
                Book selectedBook = row.getItem();
                if (selectedBook != null) {
                    new BookDetails().showBookDetails(selectedBook);
                }
            });

            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(event -> {
                Book selectedBook = row.getItem();
                if (selectedBook != null) {
                    new EditBook().displayEditBook(data, selectedBook, account);
                }
            });

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                Book selectedBook = row.getItem();
                if (selectedBook != null) {
                    new DeleteBook().deleteBook(data, selectedBook, account);
                }
            });

            MenuItem showQrItem = new MenuItem("Show QR Code");
            showQrItem.setOnAction(event -> {
                Book selectedBook = row.getItem();
                if (selectedBook != null) {
                    QrCodeGenerator qr = new QrCodeGenerator();
                    qr.showQrCode(selectedBook);
                }
            });

            contextMenu.getItems().addAll(viewDetails, editItem, deleteItem, showQrItem);

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });
    }

}