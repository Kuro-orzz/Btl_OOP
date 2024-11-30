package UI.Sidebar.Home;

import UI.Sidebar.BorrowBook.BorrowedData.BorrowedList;
import UI.Sidebar.Library.BookData.BookList;
import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.AccountList;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;

public class Home {
    private static BorderPane layout;

    public Home(BorderPane borderPane) {
        layout = borderPane;
    }
    /**
     * Get Home Stack Pane display the Home interface when click on Home button
     *
     */
    public StackPane getHomeStackPane(Account curAcc) {
        AvatarImage avatarImage = new AvatarImage(curAcc, layout);

        VBox vBox = new VBox(60);
        vBox.getChildren().addAll(avatarImage.profileButton(), statistics());

        StackPane stackPane = new StackPane(vBox);
        stackPane.setLayoutX(30);

        return stackPane;
    }

    public Rectangle initRectangle(int width, int height, Color color, int arcRadius) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(color);
        rectangle.setArcWidth(arcRadius);
        rectangle.setArcHeight(arcRadius);
        return rectangle;
    }

    public StackPane statistics() {
        Rectangle totalBooks = initRectangle(250, 150, Color.BLUE, 20);
        Rectangle totalUsers = initRectangle(250, 150, Color.GREEN, 20);
        Rectangle borrowedBooks = initRectangle(250, 150, Color.YELLOWGREEN, 20);

        HBox hBox = new HBox(60);
        hBox.getChildren().addAll(totalBooks, totalUsers, borrowedBooks);
        hBox.setAlignment(Pos.TOP_CENTER);

        BookList bookList = new BookList("books.csv");
        Text text1 = new Text("Total Books: " + bookList.getTotalBooks());

        AccountList accountList = new AccountList("accounts.csv");
        Text text2 = new Text("Total Users: " + accountList.getTotalUsers());

        BorrowedList borrowedList = new BorrowedList("borrowed.csv");
        Text text3 = new Text("Books Borrowed: " + borrowedList.getTotalBorrow());

        HBox textPane = new HBox(160, text1, text2, text3);
        textPane.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/home.css")).toExternalForm()
        );
        textPane.getStyleClass().add("text-pane");

        return new StackPane(hBox, textPane);
    }
}