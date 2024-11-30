package UI.AccessApp.Exception;

import UI.Sidebar.UserManagement.AccountData.AccountList;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;

public class RegisterException {
    public boolean isValidUsername(String username) {
        if (!username.matches("^[a-zA-Z0-9]{6,20}$")) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Username",
                    "Username must be 6-20 alphanumeric characters without special characters."
            );
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String password) {
        if (!password.matches("^[a-zA-Z0-9!@#$%^&*()_+]{6,20}$")) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Password",
                    "Password must be 6-20 characters and can include special characters."
            );
            return false;
        }
        return true;
    }

    public boolean isValidFullName(String fullName) {
        if (!fullName.matches("^[A-Z][a-z]+( [A-Z][a-z]+)+$")) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Full Name",
                    "Full name must contain at least two words, each starting with an uppercase letter."
            );
            return false;
        }
        if (fullName.length() < 6 || fullName.length() > 20) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Full Name",
                    "Full name must be between 6 and 20 characters long."
            );
            return false;
        }
        return true;
    }

    public boolean isValidAge(String age) {
        try {
            Integer.parseInt(age);
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Age",
                    "Age must be a number."
            );
            return false;
        }

        int ageInt = Integer.parseInt(age);
        if (ageInt <= 0 || ageInt >= 100) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Age",
                    "Age must be between 1 and 99."
            );
            return false;
        }
        return true;
    }

    public boolean isValidGender(Toggle gender) {
        if (gender == null) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Gender",
                    "Please select Male or Female."
            );
            return false;
        }
        return true;
    }

    public boolean isUsernameExist(String username) {
        AccountList accountList = new AccountList("accounts.csv");
        if (accountList.isUserNameExists(username)) {
            showAlert(Alert.AlertType.INFORMATION,
                    "Unavailable Username",
                    "Username already exists. Please choose a different username."
            );
            return true;
        }
        return false;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
