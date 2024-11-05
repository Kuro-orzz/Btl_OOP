package Code;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Login {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    public Scene getLoginScene(MainApp mainApp) {

        LoadImage loginImageLoader = new LoadImage();

        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-size: 100px");
        loginLabel.setTranslateY(-170);

        // Enter Username
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        usernameField.setStyle("-fx-background-color: white; -fx-opacity: 0.8; -fx-font-size: 16px; -fx-prompt-text-fill: gray;");
        usernameField.setPrefWidth(200);
        usernameField.setPrefHeight(40);
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 40) {
                // only get 40 character
                usernameField.setText(newValue.substring(0, 40));
            }
        });
        Pane pane1 = new Pane();
        pane1.getChildren().addAll(usernameField);
        pane1.setTranslateX(540);
        pane1.setTranslateY(300);

        // Enter Password
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setStyle("-fx-background-color: white; -fx-opacity: 0.8; -fx-font-size: 16px; -fx-prompt-text-fill: gray;");
        passwordField.setPrefWidth(200);
        passwordField.setPrefHeight(40);
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 40) {
                // only get 40 character
                passwordField.setText(newValue.substring(0, 40));
            }
        });

        Pane pane2 = new Pane();
        pane2.getChildren().addAll(passwordField);
        pane2.setTranslateX(540);
        pane2.setTranslateY(330);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(pane1, pane2);

        Button loginButton = new Button("Login");
        loginButton.setTranslateY(100);
        loginButton.setOnAction(event -> {
            if (authenticate(usernameField.getText(), passwordField.getText())) {
                mainApp.showSidebarScene();
            } else {
                System.out.println("Invalid Account!");
            }
        });

        StackPane stPane = new StackPane();
        stPane.getChildren().addAll(loginImageLoader.loadLoginImage(), loginLabel, vbox, loginButton);
        return new Scene(stPane, 1280, 720);
    }

    /**
     * Check if an account with username and password is valid.
     * @param username username of an account logging in
     * @param password password of an account logging in
     * @return true if account is available and false if unavailable
     */
    private boolean authenticate(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }
}
