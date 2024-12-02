package UI.AccessApp.Exception;

import UI.Sidebar.UserManagement.AccountData.AccountList;
import javafx.scene.control.Alert;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;

import java.util.Objects;

public class RegisterException {
    /**
     * Check if image exist.
     * @return true if image exist
     */
    public boolean isImageExist() {
        try {
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/GUI/loginImage.jpg")));
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Check if username valid.
     * @param username username
     * @return true if valid
     */
    public boolean isValidUsername(String username) {
        if (!username.matches("^[a-zA-Z0-9]{6,40}$")) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Username",
                    "Username must be 6-20 alphanumeric characters without special characters."
            );
            return false;
        }
        return true;
    }

    /**
     * Check if password is valid.
     * @param password password
     * @return true if valid
     */
    public boolean isValidPassword(String password) {
        if (!password.matches("^[a-zA-Z0-9!@#$%^&*()_+]{6,40}$")) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Password",
                    "Password must be 6-20 characters and can include special characters."
            );
            return false;
        }
        return true;
    }

    /**
     * Check if full name is valid.
     * @param fullName full name
     * @return true if valid
     */
    public boolean isValidFullName(String fullName) {
        if (!fullName.matches("^[a-z]+$")) {
            showAlert(Alert.AlertType.ERROR,
                    "Invalid Full Name",
                    "Full name must contain at least two words, each starting with an uppercase letter."
            );
            return false;
        }
        return true;
    }

    /**
     * Check if age is valid.
     * @param age age
     * @return true if valid
     */
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

    /**
     * Check if gender is chosen.
     * @param gender male or female
     * @return true if chosen
     */
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

    /**
     * Check if username exist or not.
     * @param username username
     * @return true if exist
     */
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

    /**
     * Show alert on screen.
     * @param alertType type of alert
     * @param title title of alert
     * @param message message about error
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
