package Controller;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.AccessApp.Login;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppController extends javafx.application.Application {
    private Stage primaryStage;
    private Login login = new Login();

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
        Image icon = new Image(getClass().getResourceAsStream("/GUI/appIcon.png"));
        stage.getIcons().add(icon);
        stage.setTitle("My Library");
        Scene loginScene = login.getLoginScene(this);
        stage.setScene(loginScene);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
        setPrimaryStage(stage);
    }

    /**
     * Present log in scene on the screen.
     */
    protected void showLoginScene() {
        this.primaryStage.setScene(login.getLoginScene(this));
    }

    public void showMainAppScene(Account acc) {
        MainApp mainApp = new MainApp(this, acc);
        Scene sidebarScene = mainApp.getMainAppScene();
        primaryStage.setScene(sidebarScene);
        primaryStage.setTitle("Library Management System");
    }

}