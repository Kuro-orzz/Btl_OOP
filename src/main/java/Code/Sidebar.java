package Code;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Sidebar {
    private Button selectedButton; // To keep track of the currently selected button which you click on it

    /**
     * Create a sidebar to choose what to do with the LMS.
     * @return sidebar scene (javafx)
     */
    public Scene getSidebarScene() {
        // default sidebar
        VBox sidebar = new VBox();
        sidebar.getStylesheets().add(getClass().getResource("/styles/sideBar.css").toExternalForm());
        sidebar.getStyleClass().add("sidebar");

        // title
        Label title = new Label("Library\nManagement");
        title.getStylesheets().add(getClass().getResource("/styles/sideBar.css").toExternalForm());
        title.getStyleClass().add("title");

        sidebar.getChildren().addAll(
                title,
                createSidebarButton("Home"),
                createSidebarButton("Library"),
                createSidebarButton("Borrow Request"),
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

    /**
     * Create button on sidebar to switch the function being use in the app.
     * @param text the button name
     * @return button (javafx)
     */
    public Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.getStylesheets().add(getClass().getResource("/styles/sideBar.css").toExternalForm());
        button.getStyleClass().add("sidebar-button");

        // To track if a button is selected or not and add hover effect
        button.setOnAction(event -> {
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("selected");
            }

            button.getStyleClass().add("selected");
            selectedButton = button;
        });

        return button;
    }
}