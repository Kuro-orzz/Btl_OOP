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
import java.util.Objects;

public class ChangePassword {
    private final Account curAcc;
    private final TextField oldPass;
    private final TextField newPass;
    private final TextField confirmPass;

    public ChangePassword(Account account) {
        this.curAcc = account;
        this.oldPass = initTextField("");
        this.newPass = initTextField("");
        this.confirmPass = initTextField("");
    }

    /**
     * Present change password.
     * @return stack pane of change password
     */
    public StackPane display() {
        Pane text1 = createTextPane(oldPass,"Old password: ");
        Pane text2 = createTextPane(newPass, "New password: ");
        Pane text3 = createTextPane(confirmPass, "Confirm password: ");

        VBox vBox = new VBox(10, text1, text2, text3);
        vBox.setTranslateX(300);

        return new StackPane(vBox);
    }

    /**
     * Init text field.
     * @param text text present on text field.
     * @return new text field
     */
    public TextField initTextField(String text) {
        TextField textField = new TextField();
        textField.setText(text);
        textField.setPrefSize(300, 40);
        textField.setStyle("-fx-font-size: 20px");
        return textField;
    }

    /**
     * Create text pane for text field.
     * @param textField text field
     * @param type text
     * @return new pane
     */
    public Pane createTextPane(TextField textField, String type) {
        Label label = new Label(type);
        label.setMinWidth(100);
        label.setStyle("-fx-font-size: 20px");
        HBox hbox = new HBox(10, label, textField);
        hbox.setAlignment(Pos.CENTER_LEFT);
        return new Pane(hbox);
    }

    /**
     * Button save password after changed.
     * @return new save button
     */
    public Button saveButton() {
        Button saveButton = new Button("Save");
        saveButton.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/home.css")).toExternalForm()
        );
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnMouseClicked(event -> {
            if (newPass.getText().equals(confirmPass.getText())) {
                curAcc.setPassword(newPass.getText());
                updateAccount(curAcc);
            }
            else {
                showPasswordMatchAlert();
            }
        });
        return saveButton;
    }

    /**
     * Update new info for account in data.
     * @param curAcc current account
     */
    static void updateAccount(Account curAcc) {
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

    /**
     * Show alert if password is not valid.
     */
    private void showPasswordMatchAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Passwords do not match");
        alert.setHeaderText(null);
        alert.setContentText("Passwords do not match");
        alert.showAndWait();
    }
}
