package Controller;

import UI.Sidebar.BorrowBook.showBorrowed;
import UI.Sidebar.BorrowRequest.showBorrowRequest;
import UI.Sidebar.Clock;
import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.Home.Home;
import UI.Sidebar.Library.Library;
import UI.Sidebar.UserManagement.userManagement;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

import java.util.Objects;
import java.util.Optional;

public class MainApp {
    private Button selectedButton; // To keep track of the currently selected button
    protected BorderPane layout = new BorderPane(); // Layout of application
    private final AppController controller;
    private final Account curAcc; // Stores if the logged-in user is an admin

    /**
     * Constructor .
     * @param controller controller
     * @param acc account of user
     */
    public MainApp(AppController controller, Account acc) {
        this.controller = controller;
        this.curAcc = acc;
    }

    /**
     * Create a sidebar to choose what to do with the LMS.
     * @return sidebar scene (javafx)
     */
    public Scene getMainAppScene() {
        // Default sidebar
        VBox sidebar = new VBox();
        sidebar.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/mainApp.css")).toExternalForm()
        );
        sidebar.getStyleClass().add("sidebar");

        // Title
        Label title = new Label("Library\nManagement");
        title.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/mainApp.css")).toExternalForm()
        );
        title.getStyleClass().add("title");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        if (curAcc.isAdmin()) {
            sidebar.getChildren().addAll(
                    title,
                    createSidebarButton("Home"),
                    createSidebarButton("Library"),
                    createSidebarButton("User Management"),
                    createSidebarButton("Borrow Request"),
                    createSidebarButton("Borrowed"),
                    createSidebarButton("Log Out"),
                    new Clock().renderClock()
            );
        } else {
            sidebar.getChildren().addAll(
                    title,
                    createSidebarButton("Home"),
                    createSidebarButton("Library"),
                    createSidebarButton("My Borrowed Book"),
                    createSidebarButton("Log Out"),
                    spacer,
                    new Clock().renderClock()
            );
        }

        layout.setLeft(sidebar);
        StackPane welcome = new StackPane();
        welcome.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/mainApp.css")).toExternalForm()
        );
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
        button.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/mainApp.css")).toExternalForm()
        );
        button.getStyleClass().add("sidebar-button");

        button.setOnAction(event -> {
            if (selectedButton != null) {
                selectedButton.getStyleClass().remove("selected");
            }

            button.getStyleClass().add("selected");
            selectedButton = button;

            // Check if the button is "Home"
            switch (text) {
                case "Home" -> {
                    Home home = new Home(layout);
                    StackPane homeView = home.getHomeStackPane(curAcc);
                    layout.setCenter(homeView);
                }
                case "Library" -> {
                    Library lib = new Library(curAcc);
                    StackPane libView = lib.getLibraryStackPane(curAcc);
                    layout.setCenter(libView);
                }
                case "User Management" -> {
                    userManagement userManagement = new userManagement(controller);
                    StackPane userView = userManagement.getUserStackPane(curAcc);
                    layout.setCenter(userView);
                }
                case "Borrow Request" -> {
                    showBorrowRequest borrow = new showBorrowRequest();
                    StackPane borrowRequestView = borrow.getBorrowRequestStackPane();
                    layout.setCenter(borrowRequestView);
                }
                case "Borrowed", "My Borrowed Book" -> {
                    showBorrowed borrowed = new showBorrowed(curAcc);
                    StackPane borrowedView = borrowed.getBorrowedStackPane(curAcc);
                    layout.setCenter(borrowedView);
                }
                case "Log Out" -> {
                    Alert logoutConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    logoutConfirmation.setTitle("Confirm Logout");
                    logoutConfirmation.setHeaderText("Are you sure to log out?");
                    logoutConfirmation.setContentText("Click OK to proceed.");
                    Optional<ButtonType> result = logoutConfirmation.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        controller.showLoginScene();
                    }
                }
            }
        });
        return button;
    }

    public BorderPane getLayout() {
        return layout;
    }
}