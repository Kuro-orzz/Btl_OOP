package Code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Image icon = new Image(getClass().getResourceAsStream("/GUI/appIcon.png"));
        stage.getIcons().add(icon);
        this.primaryStage = stage;
        showLoginScene();
    }

    private void showLoginScene() {
        Login login = new Login();
        Scene loginScene = login.getLoginScene(this);

        primaryStage.setTitle("My Library");
        primaryStage.setScene(loginScene);
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();
    }

    public void showSidebarScene() {
        Sidebar sidebar = new Sidebar();
        Scene sidebarScene = sidebar.getSidebarScene();
        primaryStage.setScene(sidebarScene);
        primaryStage.setTitle("Library Management System");
    }
}
