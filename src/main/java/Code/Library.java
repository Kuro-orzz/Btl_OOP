package Code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Library {

    private AppController controller;

    public Library(AppController controller) {
        this.controller = controller;
    }

    public StackPane getLibraryStackPane() {
        StackPane stackPane = new StackPane();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Library.fxml"));
            Parent content = fxmlLoader.load();
            stackPane.getChildren().add(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stackPane;
    }
}
