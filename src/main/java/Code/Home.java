package Code;

import AccountData.Account;
import Logic.UserInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
    public StackPane getHomeStackPane(Account currentAcc) {
        //Home label
        String label = currentAcc.isAdmin() ? "Admin Profile" : "User Profile";
        Label homeLabel = new Label(label);
        homeLabel.getStylesheets().add(getClass().getResource("/styles/home.css").toExternalForm());
        homeLabel.getStyleClass().add("home-label");

        //Home avatar
        String imgPath = currentAcc.isAdmin() ? "/GUI/adminAvatar.png" : "/GUI/userAvatar.jpg";
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

        //Profile details
        Label usernameLabel = new Label("Username: " + currentAcc.getUsername());
        Label passwordLabel = new Label("Password: " + currentAcc.getPassword());
        UserInfo userInfo = currentAcc.getInfo();
        Label fullnameLabel = new Label("Full Name: " + userInfo.getFullName());
        Label ageLabel = new Label("Age: " + userInfo.getAge());
        Label genderLabel = new Label("Gender: " + userInfo.getGender());

        VBox detailBox = new VBox(10, usernameLabel, passwordLabel, fullnameLabel, ageLabel, genderLabel);
        detailBox.setPadding(new Insets(10));

        //main StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(homeLabel, avatar, detailBox);
        stackPane.setPadding(new Insets(10, 10, 10, 10));
        StackPane.setAlignment(homeLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(avatar, Pos.CENTER);
        StackPane.setAlignment(detailBox, Pos.CENTER_RIGHT);
        return stackPane;
    }
}