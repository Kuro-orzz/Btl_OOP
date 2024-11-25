package UI.AccessApp;

import CsvFile.AppendDataToFile;
import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.AccountList;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterStage {
    private Stage register;

    /**
     * Constructor.
     *
     */
    public RegisterStage() {
        register = new Stage();
        register.setTitle("Register");
        register.setResizable(false);
        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField genderField = new TextField();
        genderField.setPromptText("Gender (true for male, false for female)");

        TextField isAdminField = new TextField();
        isAdminField.setPromptText("Is Admin (true/false)");

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String fullName = fullNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            boolean gender = Boolean.parseBoolean(genderField.getText());
            boolean isAdmin = Boolean.parseBoolean(isAdminField.getText());

            AccountList accountList = new AccountList("accounts.csv");
            if (accountList.isUserNameExists(username)) {
                showAlert(Alert.AlertType.INFORMATION,"Unavailable Username", "Username already exists. Please choose a different username.");
                return;
            } else {
                UserInfo userInfo = new UserInfo(fullName, age, gender);
                Account newAccount = new Account(username, password, isAdmin, userInfo);
                appendAccountToCSV(newAccount);
            }
            register.close();
        });

        vbox.getChildren().addAll(usernameField, passwordField, fullNameField, ageField, genderField, isAdminField, doneButton);
        Scene scene = new Scene(vbox, 300, 400);
        register.setScene(scene);
        register.show();
    }

    /**
     * Append new Account to accounts's storage file.
     * @param account account to be appended
     */
    private void appendAccountToCSV(Account account) {
        AppendDataToFile csvReader = new AppendDataToFile();
        csvReader.appendAccount("accounts.csv", account);
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
