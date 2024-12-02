package UI.AccessApp.Exception;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.util.Objects;

public class LoginException {
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
        if (!username.matches("^[a-zA-Z0-9]{1,40}$")) {
            showAlert(
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
            showAlert(
                    "Invalid Password",
                    "Password must be 6-20 characters and can include special characters."
            );
            return false;
        }
        return true;
    }

    /**
     * Show alert on screen.
     * @param title title of alert
     * @param message message about error
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
