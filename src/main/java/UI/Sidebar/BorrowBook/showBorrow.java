package UI.Sidebar.BorrowBook;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.Library.BookData.BookList;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class showBorrow {

    private TableView<Borrow> tableView;
    private ObservableList<Borrow> data;
    private FilteredList<Borrow> filteredData;

    public showBorrow(AppController controller) {
        data = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(data, e -> true);
        tableView = new TableView<>(filteredData);
        tableView.setPrefSize(896, 576);
        tableView.setStyle("-fx-border-style: solid; -fx-border-color: #CCCCCC;");
        addListFromData();
    }

    public StackPane getBorrowStackPane() {
        // Top AnchorPane for search
        AnchorPane topPane = new AnchorPane();
        topPane.setPrefSize(1024, 110);

        ComboBox<String> searchModeComboBox = new ComboBox<>();
        searchModeComboBox.getItems().addAll("Username", "ISBN", "Title");
        searchModeComboBox.setValue("Username");
        searchModeComboBox.getStylesheets().add(getClass().getResource("/styles/Lib&User.css").toExternalForm());
        searchModeComboBox.getStyleClass().add("combo-box");
        searchModeComboBox.setLayoutX(160.0);
        searchModeComboBox.setLayoutY(60.0);

        Label searchLabel = new Label("SEARCH BY");
        searchLabel.setLayoutX(180.0);
        searchLabel.setLayoutY(32.0);

        TextField searchField = new TextField();
        searchField.setLayoutX(272.0);
        searchField.setLayoutY(34.0);
        searchField.setPrefSize(500, 50);
        searchField.setStyle("-fx-background-color: #ffffff;");
        searchField.setPadding(new Insets(10, 10, 10, 10));

        topPane.getChildren().addAll(searchModeComboBox, searchLabel, searchField);

        // Define columns with cell value factories
        TableColumn<Borrow, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setPrefWidth(125);
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

        TableColumn<Borrow, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setPrefWidth(120);
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<Borrow, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setPrefWidth(600);
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Borrow, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setPrefWidth(70);
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        TableColumn<Borrow, String> borrowedDateColumn = new TableColumn<>("Borrowed Date");
        borrowedDateColumn.setPrefWidth(115);
        borrowedDateColumn.setCellValueFactory(cellData -> cellData.getValue().borrowedDateProperty());

        tableView.getColumns().addAll(usernameColumn,isbnColumn, titleColumn, statusColumn, borrowedDateColumn);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Borrow> filteredData = FXCollections.observableArrayList();
            String searchMode = searchModeComboBox.getValue();
            BorrowList list = new BorrowList("borrows.csv");
            if (newValue == null || newValue.isEmpty()) {
                filteredData.setAll(list.getBorrowList());
            } else {
                List<Borrow> searchResults = new ArrayList<>();
                switch (searchMode) {
                    case "Username":
                        searchResults = list.search("username", newValue);
                        break;
                    case "ISBN":
                        searchResults = list.search("isbn", newValue);
                        break;
                    case "Title":
                        searchResults = list.search("title", newValue);
                        break;
                    default:
                        searchResults = list.search("username", newValue);
                }
                filteredData.setAll(searchResults);
            }
            tableView.setItems(filteredData);
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

    public void addListFromData() {
        List<Borrow> list = new BorrowList("borrows.csv").getBorrowList();
        data.addAll(list);
    }
}
