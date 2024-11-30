package UI.AccessApp;

import CsvFile.AppendDataToFile;
import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.AccountList;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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

        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleButton = new RadioButton("Male");
        maleButton.setToggleGroup(genderGroup);
        maleButton.setUserData(true);

        RadioButton femaleButton = new RadioButton("Female");
        femaleButton.setToggleGroup(genderGroup);
        femaleButton.setUserData(false);

        HBox genderBox = new HBox(10, maleButton, femaleButton);

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String fullName = fullNameField.getText();
                String ageText = ageField.getText();

                if (!username.matches("^[a-zA-Z0-9]{6,20}$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Username", "Username must be 6-20 alphanumeric characters without special characters.");
                    return;
                }

                if (!password.matches("^[a-zA-Z0-9!@#$%^&*()_+]{6,20}$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Password", "Password must be 6-20 characters and can include special characters.");
                    return;
                }

                if (!fullName.matches("^[A-Z][a-z]+( [A-Z][a-z]+)+$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Full Name", "Full name must contain at least two words, each starting with an uppercase letter.");
                    return;
                }

                if (fullName.length() < 6 || fullName.length() > 20) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Full Name", "Full name must be between 6 and 20 characters long.");
                    return;
                }

                int age;
                try {
                    age = Integer.parseInt(ageText);
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Age", "Age must be a number.");
                    return;
                }

                if (age <= 0 || age >= 100) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Age", "Age must be between 1 and 99.");
                    return;
                }

                Toggle selectedGender = genderGroup.getSelectedToggle();
                if (selectedGender == null) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Gender", "Please select Male or Female.");
                    return;
                }
                boolean gender = (boolean) selectedGender.getUserData();

                boolean isAdmin = false;

                AccountList accountList = new AccountList("accounts.csv");
                if (accountList.isUserNameExists(username)) {
                    showAlert(Alert.AlertType.INFORMATION, "Unavailable Username", "Username already exists. Please choose a different username.");
                    return;
                }

                UserInfo userInfo = new UserInfo(fullName, age, gender);
                Account newAccount = new Account(username, password, isAdmin, userInfo);
                appendAccountToCSV(newAccount);

                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Account has been registered successfully!");
                register.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(usernameField, passwordField, fullNameField, ageField,
                new Label("Gender:"), genderBox, doneButton);

        Scene scene = new Scene(vbox, 300, 400);
        register.setScene(scene);
        register.show();
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
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText("Username already exists. Please choose a different username.");
        alert.showAndWait();
    }
}
