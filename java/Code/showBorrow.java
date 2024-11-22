package Code;

import AccountData.Account;
import BookData.Book;
import BookData.BookList;
import Logic.CsvReader;
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

import java.time.LocalDate;
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
            TableColumn<Borrow, Account> usernameColumn = new TableColumn<>("Username");
            usernameColumn.setPrefWidth(155);
            usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

            TableColumn<Borrow, Book> titleColumn = new TableColumn<>("Title");
            titleColumn.setPrefWidth(500);
            titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

            TableColumn<Borrow, String> statusColumn = new TableColumn<>("Status");
            statusColumn.setPrefWidth(110);
            statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

            TableColumn<Borrow, LocalDate> borrowedDateColumn = new TableColumn<>("Borrowed Date");
            borrowedDateColumn.setPrefWidth(125);
            borrowedDateColumn.setCellValueFactory(cellData -> cellData.getValue().borrowedDateProperty());

            tableView.getColumns().addAll(usernameColumn, titleColumn, statusColumn, borrowedDateColumn);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(borrow -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    String title = borrow.getTitle().getTitle();
                    return title.toLowerCase().contains(lowerCaseFilter);
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

    public void addListFromData() {
        List<Borrow> list = new BorrowList("borrow.csv").getBorrowList();
        data.addAll(list);
    }
}
