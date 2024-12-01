package Controller;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.AccessApp.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class AppController extends Application {
    private Stage primaryStage;
    private final Login login = new Login(this);

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Image icon = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/GUI/appIcon.png"))
        );
        stage.getIcons().add(icon);
        stage.setTitle("My Library");
        Scene loginScene = login.getLoginScene();
        stage.setScene(loginScene);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
        setPrimaryStage(stage);
    }

    /**
     * Present log in scene on the screen.
     */
    public void showLoginScene() {
        this.primaryStage.setScene(login.getLoginScene());
    }

    public void showMainAppScene(Account acc) {
        MainApp mainApp = new MainApp(this, acc);
        Scene sidebarScene = mainApp.getMainAppScene();
        primaryStage.setScene(sidebarScene);
        primaryStage.setTitle("Library Management System");
    }

}