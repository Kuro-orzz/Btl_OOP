package Code;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Sidebar {
    public Scene getSidebarScene() {
        VBox sidebar = new VBox();
        sidebar.setPadding(new Insets(30));
        sidebar.setSpacing(5);
        sidebar.setStyle("-fx-background-color: #1D2D50;");

        sidebar.getChildren().addAll(
                createSidebarButton("Dashboard"),
                createSidebarButton("Library"),
                createSidebarButton("List Borrow"),
                createSidebarButton("Processing"),
                createSidebarButton("Borrow"),
                createSidebarButton("Return"),
                createSidebarButton("Search"),
                createSidebarButton("Statistic")
        );

        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);
        return new Scene(layout, 1280, 720);
    }

    private Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setStyle("-fx-font-size: 20px; -fx-background-color: #243A73; -fx-text-fill: white; -fx-alignment: center-left;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 20px; -fx-background-color: #3A4E8D; -fx-text-fill: white; -fx-alignment: center-left;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 20px; -fx-background-color: #243A73; -fx-text-fill: white; -fx-alignment: center-left;"));
        return button;
    }
}
