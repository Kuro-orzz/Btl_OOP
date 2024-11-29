package UI.AccessApp;

import Controller.AppController;
import CsvFile.GetDataFromFile;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.AccountList;

import java.util.List;

public class Login {
    private Account curAcc;

    public Login() {}

    /**
     * Create a scene where we log in.
     * @param appController main app controller
     * @return login scene to be showed
     */
    public Scene getLoginScene(AppController appController) {
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
        Button loginButton = createLoginButton(appController, usernameField, passwordField);

        // Add Enter key handler to username and password fields
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(appController, usernameField, passwordField);
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(appController, usernameField, passwordField);
            }
        });

        Button registerButton = new Button("New Account");
        registerButton.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        registerButton.getStyleClass().add("register-button");
        registerButton.setOnAction(event -> {
            GetDataFromFile csvReader = new GetDataFromFile();
            List<Account> accounts = csvReader.getAccountsFromFile("accounts.csv");
            Account newSetting = new Account();
            newSetting.setCounter(accounts.get(accounts.size() - 1).getId() + 1);
            RegisterStage registerStage = new RegisterStage();
        });

        StackPane stPane = new StackPane();
        stPane.getChildren().addAll(backgroundImage, loginLabel, vbox, loginButton,registerButton );

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
                // only get 40 characters
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
     * @param appController the main app controller
     * @param usernameField where to get username
     * @param passwordField where to get password
     * @return login Button
     */
    public Button createLoginButton(AppController appController, TextInputControl usernameField, TextInputControl passwordField) {
        Button loginButton = new Button("Login");
        loginButton.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        loginButton.getStyleClass().add("login-button");
        loginButton.setOnMouseClicked(event -> handleLogin(appController, usernameField, passwordField));
        return loginButton;
    }

    /**
     * Handle login logic.
     * @param appController the main app controller
     * @param usernameField where to get username
     * @param passwordField where to get password
     */
    private void handleLogin(AppController appController, TextInputControl usernameField, TextInputControl passwordField) {
        if (authenticate(usernameField.getText(), passwordField.getText())) {
            AccountList accountList = new AccountList("accounts.csv");
            Account account = accountList.getAccountByUsername(usernameField.getText());
            boolean isAdmin = account.isAdmin();
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + account.getInfo().getFullName());
            appController.showMainAppScene(curAcc);
        } else {
             showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    /**
     * Check if an account with username and password is valid.
     * @param username username of an account logging in
     * @param password password of an account logging in
     * @return true if account is available and false if unavailable
     */
    private boolean authenticate(String username, String password) {
        List<Account> list = new AccountList("accounts.csv").getAccountList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)
                    && list.get(i).getPassword().equals(password)) {
                curAcc = list.get(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Show alert pop-up.
     * @param alertType type of alert
     * @param title title of alert
     * @param message message
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}