package Code;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;

import Logic.Account;
import Logic.AccountList;

public class Login {
    protected AccountList accountList = new AccountList();

    public Login() {
        Account account = new Account("admin", "password", true);
        accountList.addAccount(account);
    }

    /**
     * Create a scene where we log in.
     * @param mainApp main app controller
     * @return login scene to be showed
     */
    public Scene getLoginScene(MainApp mainApp) {
        ImageView backgroundImage = new ImageView();
        try {
            Image loginImage = new Image(getClass().getResourceAsStream("/GUI/loginImage.jpg"));
            backgroundImage.setImage(loginImage);
            backgroundImage.setFitWidth(1280);
            backgroundImage.setFitHeight(720);
            backgroundImage.setStyle("-fx-opacity: 0.6");
            backgroundImage.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

        // login label
        Label loginLabel = new Label("     Library\nManagement");
        loginLabel.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        loginLabel.getStyleClass().add("login-label");

        // Enter Username
        TextInputControl usernameField = createInputField("username");
        Pane usernamePane = createPane(usernameField, 530, 330);

        // Enter Password
        TextInputControl passwordField = createInputField("password");
        Pane passwordPane = createPane(passwordField, 530, 350);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(usernamePane, passwordPane);

        // Login button
        Button loginButton = createLoginButton(mainApp, usernameField, passwordField);

        StackPane stPane = new StackPane();
        stPane.getChildren().addAll(backgroundImage, loginLabel, vbox, loginButton);

        

        return new Scene(stPane, 1280, 720);
    }

    /**
     * Create text input field to get username or password from keyboard.
     * @param type select which field to get password and which to get username
     * @return Text input field
     */
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
        assert textInput != null;
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

    /**
     * Create a pane including text field with position (X,Y)
     * @param textInput text field
     * @param posX x index for positioning
     * @param posY y index for positioning
     * @return Pane
     */
    public Pane createPane(TextInputControl textInput, int posX, int posY) {
        Pane pane = new Pane(textInput);
        pane.setTranslateX(posX);
        pane.setTranslateY(posY);
        return pane;
    }

    /**
     * Create button to log in when click on it.
     * @param mainApp the main app controller
     * @param usernameField where to get username
     * @param passwordField where to get password
     * @return login Button
     */
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
        Account accountToCheck = new Account(username, password);
        return accountList.searchAccounts(accountToCheck);
    }
}