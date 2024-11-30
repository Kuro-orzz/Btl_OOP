package UI.AccessApp;

import Controller.AppController;
import CsvFile.GetDataFromFile;
import UI.AccessApp.Exception.LoginException;
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
import java.util.Objects;

public class Login extends LoginException {
    private final AppController controller;
    private Account curAcc;
    private final Label loginLabel;
    private final TextInputControl usernameField;
    private final TextInputControl passwordField;
    private final Button loginButton;
    private final Button registerButton;

    public Login(AppController controller) {
        this.controller = controller;
        this.loginLabel = new Label("     Library\nManagement");
        this.usernameField = createInputField("username");
        this.passwordField = createInputField("password");
        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");
    }

    /**
     * Create a scene where we log in.
     * @return login scene
     */
    public Scene getLoginScene() {
        // background
        ImageView backgroundImage = new ImageView();
        backgroundImage.setImage(loadImage());
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitWidth(1280);
        backgroundImage.setFitHeight(720);
        backgroundImage.setStyle("-fx-opacity: 0.6");

        // login label
        loginLabel.getStyleClass().add("login-label");

        // text input
        Pane usernamePane = createPane(usernameField, 530, 330);
        Pane passwordPane = createPane(passwordField, 530, 350);
        VBox vbox = new VBox(usernamePane, passwordPane);

        // login button
        initButton("username");
        addTextInputEvent();

        // register button
        initButton("register");
        addRegisterButtonEvent();

        StackPane stPane = new StackPane(backgroundImage, loginLabel,
                vbox, loginButton, registerButton);

        Scene scene = new Scene(stPane, 1280, 720);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/login.css")).toExternalForm()
        );

        return scene;
    }

    public Image loadImage() {
        if (!isImageExist()) {
            return null;
        }
        return new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/GUI/loginImage.jpg"))
        );
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
     * Create text input field to get username or password from keyboard.
     * @param type select which field to get password and which to get username
     * @return Text input field
     */
    public TextInputControl createInputField(String type) {
        TextInputControl textInput = type.equals("username") ? new TextField() : new PasswordField();
        textInput.setPromptText(type.equals("username") ? "Enter Username" : "Enter Password");

        textInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 40) {
                textInput.setText(newValue.substring(0, 40));
            }
        });
        textInput.getStyleClass().add("text-input");

        return textInput;
    }

    public void addTextInputEvent() {
        // Add Enter key handler to username and password fields
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
    }

    public void addRegisterButtonEvent() {
        registerButton.setOnAction(event -> {
            List<Account> accounts = new GetDataFromFile().getAccountsFromFile("accounts.csv");
            if (!accounts.isEmpty()) {
                Account.setCounter(accounts.get(accounts.size() - 1).getId() + 1);
            } else {
                Account.setCounter(1);
            }
            new RegisterStage().display();
        });
    }

    /**
     * Create button to log in when click on it.
     */
    public void initButton(String type) {
        if (type.equals("username")) {
            loginButton.getStyleClass().add("login-button");
            loginButton.setOnMouseClicked(event -> handleLogin());
        } else if (type.equals("register")) {
            registerButton.getStyleClass().add("register-button");
        }
    }

    /**
     * Handle login logic.
     */
    private void handleLogin() {
        if (!isValidUsername(usernameField.getText()) || !isValidPassword(passwordField.getText())) {
            return;
        }
        if (authenticate(usernameField.getText(), passwordField.getText())) {
            showAlert(Alert.AlertType.INFORMATION,
                    "Login Successful",
                    "Welcome " + curAcc.getInfo().getFullName());
            controller.showMainAppScene(curAcc);
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
        for (Account account : list) {
            if (account.getUsername().equals(username)
                    && account.getPassword().equals(password)) {
                curAcc = account;
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