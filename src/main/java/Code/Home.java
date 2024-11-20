package Code;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public class Home {
    private AppController controller;

    public Home(AppController controller) {
        this.controller = controller;
    }
    /**
     * Get Home Stack Pane display the Home interface when click on Home button
     *
     */
    public StackPane getHomeStackPane(boolean isAdmin) {
        //Home label
        String label = isAdmin ? "Admin" : "User";
        Label homeLabel = new Label(label);
        homeLabel.getStylesheets().add(getClass().getResource("/styles/home.css").toExternalForm());
        homeLabel.getStyleClass().add("home-label");

        //Home avatar
        String imgPath = isAdmin ? "/GUI/adminAvatar.png" : "/GUI/userAvatar.jpg";
        ImageView avatar = new ImageView();
        try {
            Image avatarImage = new Image(getClass().getResourceAsStream(imgPath));
            avatar.setImage(avatarImage);
            avatar.setFitWidth(200);
            avatar.setFitHeight(200);
            avatar.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
        //Logout button
        Button logoutButton = new Button("Log out");
        logoutButton.getStylesheets().add(getClass().getResource("/styles/home.css").toExternalForm());
        logoutButton.getStyleClass().add("logout-button");
        logoutButton.setOnAction(event -> {
            Alert logoutConfirmatio = new Alert(Alert.AlertType.CONFIRMATION);
            logoutConfirmatio.setTitle("Confirm Logout");
            logoutConfirmatio.setHeaderText("Are you sure to log out?");
            logoutConfirmatio.setContentText("Click OK to proceed.");
            Optional<ButtonType> result = logoutConfirmatio.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                controller.showLoginScene();
            }
        });

        //main StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(homeLabel, avatar, logoutButton);
        stackPane.setPadding(new Insets(10, 10, 10, 10));
        StackPane.setAlignment(homeLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(logoutButton, Pos.BOTTOM_RIGHT);
        return stackPane;
    }
}