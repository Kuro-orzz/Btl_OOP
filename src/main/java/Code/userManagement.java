package Code;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class userManagement {

    private AppController controller;

    public userManagement(AppController controller) {
        this.controller = controller;
    }

    public StackPane getUserStackPane() {
        StackPane stackPane = new StackPane();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/userList.fxml"));
            Parent content = fxmlLoader.load();
            stackPane.getChildren().add(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stackPane;
    }
}
