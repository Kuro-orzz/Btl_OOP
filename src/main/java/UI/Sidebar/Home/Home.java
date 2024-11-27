package UI.Sidebar.Home;

import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowBook.BorrowedData.BorrowedList;
import UI.Sidebar.Library.BookData.BookList;
import UI.Sidebar.UserManagement.AccountData.Account;
import Controller.AppController;
import UI.Sidebar.UserManagement.AccountData.AccountList;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Home {
    private AppController controller;

    public Home(AppController controller) {
        this.controller = controller;
    }
    /**
     * Get Home Stack Pane display the Home interface when click on Home button
     *
     */
    public StackPane getHomeStackPane(Account currentAcc) {
        Rectangle totalBooks = new Rectangle(250, 150);
        totalBooks.setFill(Color.BLUE);
        Rectangle totalUsers = new Rectangle(250, 150);
        totalUsers.setFill(Color.GREEN);
        Rectangle bookBorrower = new Rectangle(250, 150);
        bookBorrower.setFill(Color.YELLOWGREEN);

        BookList bookList = new BookList("books.csv");
        Text text1 = new Text("Total Books: " + bookList.getTotalBooks());
        text1.setFill(Color.WHITE);
        text1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        AccountList accountList = new AccountList("accounts.csv");
        Text text2 = new Text("Total Users: " + accountList.getTotalUsers());
        text2.setFill(Color.WHITE);
        text2.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        BorrowedList borrowedList = new BorrowedList("borrowed.csv");
        Text text3 = new Text("Books Borrowed: " + borrowedList.getTotalBorrow());
        text3.setFill(Color.WHITE);
        text3.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Pane pane = new Pane();

        totalBooks.setLayoutX(80);
        totalBooks.setLayoutY(50);
        totalUsers.setLayoutX(380);
        totalUsers.setLayoutY(50);
        bookBorrower.setLayoutX(680);
        bookBorrower.setLayoutY(50);

        text1.setX(125);
        text1.setY(130);
        text2.setX(445);
        text2.setY(130);
        text3.setX(725);
        text3.setY(130);

        pane.getChildren().addAll(totalBooks, text1, totalUsers, text2, bookBorrower, text3);

        ObservableList<Borrowed> users = FXCollections.observableArrayList(
                new Borrowed("user2", "8"),
                new Borrowed("user1", "8"),
                new Borrowed("user4", "7"),
                new Borrowed("user3", "7"),
                new Borrowed("user5", "7")
        );

        TableView<Borrowed> table1 = new TableView<>(users);

        TableColumn<Borrowed, String> usernameCol = new TableColumn<>("ID");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setPrefWidth(150);

        TableColumn<Borrowed, Integer> total1Col = new TableColumn<>("Total");
        total1Col.setCellValueFactory(new PropertyValueFactory<>("total"));
        total1Col.setPrefWidth(150);

        Text topUserText = new Text("Top User Borrowed");
        topUserText.setFill(Color.BLACK);
        topUserText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        topUserText.setX(60);
        topUserText.setY(320);

        Text topBookText = new Text("Top Book Borrowed");
        topBookText.setFill(Color.BLACK);
        topBookText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        topBookText.setX(500);
        topBookText.setY(320);

        table1.getColumns().addAll(usernameCol, total1Col);
        table1.setMaxWidth(300);
        table1.setMaxHeight(255);

        ObservableList<Borrowed> isbn = FXCollections.observableArrayList(
                new Borrowed("123", "8"),
                new Borrowed("124", "8"),
                new Borrowed("125", "7"),
                new Borrowed("126", "7"),
                new Borrowed("127", "7")
        );

        TableView<Borrowed> table2 = new TableView<>(isbn);

        TableColumn<Borrowed, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnCol.setPrefWidth(150);

        TableColumn<Borrowed, Integer> total2Col = new TableColumn<>("Total");
        total2Col.setCellValueFactory(new PropertyValueFactory<>("total"));
        total2Col.setPrefWidth(150);

        table2.getColumns().addAll(isbnCol, total2Col);
        table2.setMaxWidth(300);
        table2.setMaxHeight(255);

        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(pane, 50.0);
        AnchorPane.setLeftAnchor(pane, 10.0);
        AnchorPane.setTopAnchor(table1, 350.0);
        AnchorPane.setLeftAnchor(table1, 60.0);
        AnchorPane.setTopAnchor(table2, 350.0);
        AnchorPane.setLeftAnchor(table2, 500.0);

        anchorPane.getChildren().addAll(pane,topUserText, table1,topBookText, table2);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(anchorPane);

        return stackPane;
    }
}