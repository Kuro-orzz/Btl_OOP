package Code;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Sidebar {
    public Scene getSidebarScene() {
        // default sidebar
        VBox sidebar = new VBox();
        sidebar.getStylesheets().add(getClass().getResource("/styles/sidebar.css").toExternalForm());
        sidebar.getStyleClass().add("sidebar");

        // title
        Label title = new Label("Library\nmanagement");
        title.getStylesheets().add(getClass().getResource("/styles/sidebar.css").toExternalForm());
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

    public Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.getStylesheets().add(getClass().getResource("/styles/sidebar.css").toExternalForm());
        button.getStyleClass().add("sidebar-button");
        return button;
    }
}
