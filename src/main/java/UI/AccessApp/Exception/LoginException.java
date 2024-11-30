package UI.AccessApp.Exception;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.util.Objects;

public class LoginException {
    public boolean isImageExist() {
        try {
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/GUI/loginImage.jpg")));
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean isValidUsername(String username) {
        if (!username.matches("^[a-zA-Z0-9]{4,20}$")) {
            showAlert(
                    "Invalid Username",
                    "Username must be 6-20 alphanumeric characters without special characters."
            );
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String password) {
        if (!password.matches("^[a-zA-Z0-9!@#$%^&*()_+]{4,20}$")) {
            showAlert(
                    "Invalid Password",
                    "Password must be 6-20 characters and can include special characters."
            );
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
