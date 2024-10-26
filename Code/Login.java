package Code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {
    public void start(Stage stage) {
        // Background
        LoadImage LOGIN_IMG = new LoadImage();

        // Text "LOGIN"
        Label LOGIN = new Label("Login");
        LOGIN.setStyle("-fx-font-size: 100px");
        LOGIN.setTranslateX(0);
        LOGIN.setTranslateY(-170);

        // Enter Username
        TextField loginField = new TextField();
        loginField.setPromptText("Enter Username");
        loginField.setStyle("-fx-background-color: white; -fx-opacity: 0.8; -fx-font-size: 16px; -fx-prompt-text-fill: gray;");
        loginField.setPrefWidth(200);
        loginField.setPrefHeight(40);
        loginField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 40) {
                // only get 40 character
                loginField.setText(newValue.substring(0, 40));
            }
        });
        Pane pane1 = new Pane();
        pane1.getChildren().addAll(loginField);
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

        // create button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setPrefWidth(100);
        loginButton.setPrefHeight(40);
        loginButton.setTranslateX(0);
        loginButton.setTranslateY(100);

        loginButton.setOnAction(event -> {
            String username = loginField.getText();
            String password = passwordField.getText();
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        });

        // Add all to stack pane
        StackPane stpane = new StackPane();
        stpane.getChildren().addAll(LOGIN_IMG.loadLoginImage(), LOGIN, vbox, loginButton);

        // show all element to screen
        Scene scene = new Scene(stpane, 1280, 720);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
