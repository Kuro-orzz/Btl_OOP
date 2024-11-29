package UI.Sidebar.Home.ContextMenu;

import CsvFile.UpdateDataFromListToFile;
import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.AccountList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.List;

public class ChangePassword {
    private Account curAcc;
    private BorderPane layout;
    private TextField oldPass, newPass, confirmPass;

    public ChangePassword(Account curAcc, BorderPane layout) {
        this.curAcc = curAcc;
        this.layout = layout;
        oldPass = new TextField();
        newPass = new TextField();
        confirmPass = new TextField();
    }

    public StackPane display() {
        Pane text1 = createTextPane(oldPass, "", "Old password: ");
        Pane text2 = createTextPane(newPass, "", "New password: ");
        Pane text3 = createTextPane(confirmPass, "", "Confirm password: ");

        VBox vBox = new VBox(10, text1, text2, text3);
        vBox.setTranslateX(300);
        return new StackPane(vBox);
    }

    public Pane createTextPane(TextField textField, String text, String type) {
        textField.setText(text);
        textField.setPrefSize(300, 40);
        textField.setStyle("-fx-font-size: 20px");
        Label label = new Label(type);
        label.setMinWidth(200);
        label.setStyle("-fx-font-size: 20px");
        HBox hbox = new HBox(10, label, textField);
        hbox.setAlignment(Pos.CENTER_LEFT);
        return new Pane(hbox);
    }

    public Button saveButton() {
        Button saveButton = new Button("Save");
        saveButton.getStylesheets().add(getClass().getResource("/styles/home.css").toExternalForm());
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnMouseClicked(event -> {
            if (newPass.getText().equals(confirmPass.getText())) {
                curAcc.setPassword(newPass.getText());
                List<Account> list = new AccountList("accounts.csv").getAccountList();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId() == curAcc.getId()) {
                        list.set(i, curAcc);
                        break;
                    }
                }
                UpdateDataFromListToFile update = new UpdateDataFromListToFile();
                update.updateAccounts("accounts.csv", list);
            }
            else {
                showAlert(Alert.AlertType.ERROR, "Passwords do not match", "Passwords do not match");
            }
        });
        return saveButton;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
