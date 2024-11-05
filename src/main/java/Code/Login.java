package Code;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;

public class Login {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    public Scene getLoginScene(MainApp mainApp) {

        LoadImage loginImageLoader = new LoadImage();

        // login label
        Label loginLabel = new Label("Login");
        loginLabel.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        loginLabel.getStyleClass().add("login-label");

        // Enter Username
        TextInputControl usernameField = createInputField("username");
        Pane usernamePane = createPane(usernameField, 540, 300);

        // Enter Password
        TextInputControl passwordField = createInputField("password");
        Pane passwordPane = createPane(passwordField, 540, 330);

        // Login button
        Button loginButton = createLoginButton(mainApp, usernameField, passwordField);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(usernamePane, passwordPane);

        StackPane stPane = new StackPane();
        stPane.getChildren().addAll(loginImageLoader.loadLoginImage(), loginLabel, vbox, loginButton);

        return new Scene(stPane, 1280, 720);
    }

    public TextInputControl createInputField(String type) {
        TextInputControl textInput = null;
        if (type.equals("username")) {
            textInput = new TextField();
            textInput.setPromptText("Enter Username");
        } else if (type.equals("password")) {
            textInput = new PasswordField();
            textInput.setPromptText("Enter Password");
        }
        TextInputControl finalTextInput = textInput;
        textInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 40) {
                // only get 40 character
                finalTextInput.setText(newValue.substring(0, 40));
            }
        });

        textInput.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        textInput.getStyleClass().add("text-input");

        return textInput;
    }

    public Pane createPane(TextInputControl textInput, int posX, int posY) {
        Pane pane = new Pane(textInput);
        pane.setTranslateX(posX);
        pane.setTranslateY(posY);
        return pane;
    }

    public Button createLoginButton(MainApp mainApp, TextInputControl usernameField, TextInputControl passwordField) {
        Button loginButton = new Button("Login");
        loginButton.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        loginButton.getStyleClass().add("login-button");
        loginButton.setOnAction(event -> {
            if (authenticate(usernameField.getText(), passwordField.getText())) {
                mainApp.showSidebarScene();
            } else {
                System.out.println("Invalid Account!");
            }
        });
        return loginButton;
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
