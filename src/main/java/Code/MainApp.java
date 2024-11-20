package Code;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainApp {
    private Button selectedButton; // To keep track of the currently selected button
    protected BorderPane layout = new BorderPane(); // Layout of application
    private AppController controller = new AppController();
    private boolean isAdmin; // Stores if the logged-in user is an admin

    public MainApp(AppController controller, boolean isAdmin) {
        this.controller = controller;
        this.isAdmin = isAdmin;
    }

    /**
     * Create a sidebar to choose what to do with the LMS.
     * @return sidebar scene (javafx)
     */
    public Scene getMainAppScene() {
        // Default sidebar
        VBox sidebar = new VBox();
        sidebar.getStylesheets().add(getClass().getResource("/styles/mainApp.css").toExternalForm());
        sidebar.getStyleClass().add("sidebar");

        // Title
        Label title = new Label("Library\nManagement");
        title.getStylesheets().add(getClass().getResource("/styles/mainApp.css").toExternalForm());
        title.getStyleClass().add("title");

        sidebar.getChildren().addAll(
                title,
                createSidebarButton("Home"),
                createSidebarButton("Library"),
                createSidebarButton("User Management"),
                createSidebarButton("Borrow Request"),
                createSidebarButton("Borrow"),
                createSidebarButton("Return")
        );

        layout.setLeft(sidebar);
        StackPane welcome = new StackPane();
        welcome.getStylesheets().add(getClass().getResource("/styles/mainApp.css").toExternalForm());
        Label welcomeLabel = new Label("Welcome to Library!");
        welcomeLabel.getStyleClass().add("welcome-label");
        welcome.setStyle("-fx-background-color: #F2F4F7;");
        welcome.getChildren().addAll(welcomeLabel);
        layout.setCenter(welcome);

        return new Scene(layout, 1280, 720);
    }

    /**
     * Create button on sidebar to switch the function being used in the app.
     * @param text the button name
     * @return button (javafx)
     */
    public Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.getStylesheets().add(getClass().getResource("/styles/mainApp.css").toExternalForm());
        button.getStyleClass().add("sidebar-button");

        button.setOnAction(event -> {
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("selected");
            }

            button.getStyleClass().add("selected");
            selectedButton = button;

            // Check if the button is "Home"
            if (text.equals("Home")) {
                Home home = new Home(controller);
                StackPane homeView = home.getHomeStackPane(isAdmin);
                layout.setCenter(homeView);
            } else if (text.equals("Library")) {
                Library lib = new Library(controller);
                StackPane libView = lib.getLibraryStackPane();
                layout.setCenter(libView);
            } else if (text.equals("User Management")) {
                userManagement userManagement = new userManagement(controller);
                StackPane userView = userManagement.getUserStackPane();
                layout.setCenter(userView);
            }
        });

        return button;
    }
}
