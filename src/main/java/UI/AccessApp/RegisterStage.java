package UI.AccessApp;

import CsvFile.AppendDataToFile;
import UI.AccessApp.Exception.RegisterException;
import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterStage extends RegisterException {
    private final Stage register;
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
    public RegisterStage() {
        register = new Stage();
        this.usernameField = new TextField();
        this.passwordField = new TextField();
        this.fullNameField = new TextField();
        this.ageField = new TextField();
        this.genderGroup = new ToggleGroup();
        this.maleButton = new RadioButton("Male");
        this.femaleButton = new RadioButton("Female");
        this.registerButton = new Button("Register");
    }

    public void display() {
        defaultSetting();
        addButtonAction();

        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");

        HBox genderBox = new HBox(10, maleButton, femaleButton);
        Label genderLabel = new Label("Gender");

        vbox.getChildren().addAll(usernameField, passwordField, fullNameField, ageField,
                genderLabel, genderBox, registerButton);

        Scene scene = new Scene(vbox, 300, 400);
        register.setScene(scene);
        register.show();
    }

    public void defaultSetting() {
        register.setTitle("Register");
        register.setResizable(false);

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        fullNameField.setPromptText("Full Name");
        ageField.setPromptText("Age");

        maleButton.setToggleGroup(genderGroup);
        maleButton.setUserData(true);

        femaleButton.setToggleGroup(genderGroup);
        femaleButton.setUserData(false);
    }

    public void addButtonAction() {
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
            register.close();
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
