package UI.AccessApp;

import Controller.AppController;
import CsvFile.AppendDataToFile;
import UI.AccessApp.Exception.RegisterException;
import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class RegisterStage extends RegisterException {
    private final AppController controller;
    private final Stage stage;
    private final ImageView backgroundImage;
    private final ImageView backToLoginScene;
    private final Label registerLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField fullNameField;
    private final TextField ageField;
    private final ToggleGroup genderGroup;
    private final RadioButton maleButton;
    private final RadioButton femaleButton;
    private final Button registerButton;

    /**
     * Constructor.
     */
    public RegisterStage(AppController controller) {
        this.controller = controller;
        this.stage = controller.getPrimaryStage();
        this.backgroundImage = new ImageView();
        this.backToLoginScene = new ImageView();
        this.registerLabel = new Label("Register");
        this.usernameField = createInputField("Enter username", "username");
        this.passwordField = createInputField("Enter Password", "password");
        this.fullNameField = createInputField("Enter full name", "full name");
        this.ageField = createInputField("Enter age", "age");
        this.genderGroup = new ToggleGroup();
        this.maleButton = new RadioButton("Male");
        this.femaleButton = new RadioButton("Female");
        this.registerButton = new Button("Register");
        defaultSetting();
    }

    /**
     * Present register on screen.
     */
    public void display() {
        // background
        if (!isImageExist()) {
            return;
        }
        loadImage();

        // label
        registerLabel.getStyleClass().add("register-label");

        // gender vbox
        HBox genderBox = new HBox(10, maleButton, femaleButton);

        // text input
        Pane usernamePane = createPane(usernameField, 530, 180);
        Pane passwordPane = createPane(passwordField, 530, 200);
        Pane fullNamePane = createPane(fullNameField, 530, 220);
        Pane agePane = createPane(ageField, 530, 240);
        Pane genderPane = createPane(genderBox, 530, 260);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(usernamePane, passwordPane,
                fullNamePane, agePane, genderPane);

        // button
        addButtonAction();

        StackPane stPane = new StackPane(backgroundImage, registerLabel,
                vbox, registerButton, backToLoginScene);

        Scene scene = new Scene(stPane, 1280, 720);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/login.css")).toExternalForm()
        );
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Default setting for gender.
     */
    public void defaultSetting() {
        maleButton.setToggleGroup(genderGroup);
        maleButton.setUserData(true);
        maleButton.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        femaleButton.setToggleGroup(genderGroup);
        femaleButton.setUserData(false);
        femaleButton.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
    }

    /**
     * Load image.
     */
    public void loadImage() {
        backgroundImage.setImage(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/GUI/loginImage.jpg"))
        ));
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitWidth(1280);
        backgroundImage.setFitHeight(720);
        backgroundImage.setStyle("-fx-opacity: 0.6");

        backToLoginScene.setImage(new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/GUI/return.png"))
        ));
        backToLoginScene.setPreserveRatio(true);
        backToLoginScene.setFitWidth(70);
    }

    /**
     * Init text field for input.
     * @param text text
     * @param type normal text field or password
     * @return new text field
     */
    public TextField createInputField(String text, String type) {
        TextField textInput = type.equals("password") ? new PasswordField() : new TextField();
        textInput.setPromptText(text);

        textInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 40) {
                textInput.setText(newValue.substring(0, 40));
            }
        });
        textInput.getStyleClass().add("text-input");

        return textInput;
    }

    /**
     * Create pane from node.
     * @param node node.
     * @param posX position on screen
     * @param posY position on screen
     * @return new pane
     */
    public Pane createPane(Node node, int posX, int posY) {
        Pane pane = new Pane(node);
        pane.setTranslateX(posX);
        pane.setTranslateY(posY);
        return pane;
    }

    /**
     * Add action for button.
     */
    public void addButtonAction() {
        backToLoginScene.getStyleClass().add("return");
        backToLoginScene.setOnMouseClicked(e -> controller.showLoginScene());
        backToLoginScene.setOnMouseEntered(e -> {
            backToLoginScene.setScaleX(1.2); // Increase width
            backToLoginScene.setScaleY(1.2); // Increase height
        });

        backToLoginScene.setOnMouseExited(e -> {
            backToLoginScene.setScaleX(1.0); // Reset width
            backToLoginScene.setScaleY(1.0); // Reset height
        });

        registerButton.getStyleClass().add("register-button");
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String fullName = fullNameField.getText();
            String ageText = ageField.getText();

            if (!isValidUsername(username) || !isValidPassword(password) ||
                    !isValidFullName(fullName) || !isValidAge(ageText)) {
                return;
            }
            int age = Integer.parseInt(ageText);

            Toggle selectedGender = genderGroup.getSelectedToggle();
            if (!isValidGender(selectedGender)) {
                return;
            }
            boolean gender = (boolean) selectedGender.getUserData();

            if (isUsernameExist(username)) {
                return;
            }

            UserInfo userInfo = new UserInfo(fullName, age, gender);
            Account newAccount = new Account(username, password, false, userInfo);
            appendAccountToCSV(newAccount);

            showAlert();
            controller.showMainAppScene(newAccount);
        });
    }

    /**
     * Append new Account to data.
     * @param account account to be appended
     */
    private void appendAccountToCSV(Account account) {
        AppendDataToFile csvReader = new AppendDataToFile();
        csvReader.appendAccount("accounts.csv", account);
    }

    /**
     * Show alert pop-up.
     */
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Account has been registered successfully!");
        alert.showAndWait();
    }
}
