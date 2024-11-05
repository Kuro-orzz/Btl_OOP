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
        sidebar.setSpacing(10);
        sidebar.setStyle("-fx-background-color: #1D2D50;");

        Label title = new Label("Library\nmanagement");
        title.getStylesheets().add(getClass().getResource("/styles/sideBar.css").toExternalForm());
        title.getStyleClass().add("title");

        sidebar.getChildren().addAll(
                title,
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
        button.getStylesheets().add(getClass().getResource("/styles/sideBar.css").toExternalForm());
        button.getStyleClass().add("sidebar-button");
        return button;
    }
}
