package UI.Sidebar.Home.ContextMenu;

import CsvFile.UpdateDataFromListToFile;
import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.AccountList;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Objects;

public class EditProfile {
    private Account curAcc;
    private BorderPane layout;
    private TextField fullName, age, gender, phone, address, email;

    public EditProfile(Account curAcc, BorderPane layout) {
        this.curAcc = curAcc;
        this.layout = layout;
        this.fullName = new TextField();
        this.age = new TextField();
        this.gender = new TextField();
        this.phone = new TextField();
        this.address = new TextField();
        this.email = new TextField();
    }

    public StackPane displayEdit() {
        UserInfo info = curAcc.getInfo();
        Pane text1 = createTextPane(fullName, info.getFullName(), "Full name:");
        Pane text2 = createTextPane(age, Integer.toString(info.getAge()), "Age:");
        Pane text3 = createTextPane(gender, info.getGender() ? "Male" : "Female", "Gender:");
        Pane text4 = createTextPane(phone, info.getPhoneNumber(), "Phone:");
        Pane text5 = createTextPane(address, info.getAddress(), "Address:");
        Pane text6 = createTextPane(email, info.getEmail(), "Email:");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(text1, text2, text3, text4, text5, text6);
        vbox.setTranslateX(300);

        return new StackPane(vbox);
    }

    public Pane createTextPane(TextField textField, String text, String type) {
        textField.setText(text);
        textField.setPrefSize(300, 40);
        textField.setStyle("-fx-font-size: 20px");
        Label label = new Label(type);
        label.setMinWidth(100);
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
            UserInfo info = curAcc.getInfo();
            info.setFullName(fullName.getText());
            info.setAge(Integer.parseInt(age.getText()));
            info.setPhoneNumber(phone.getText());
            info.setAddress(address.getText());
            info.setEmail(email.getText());
            curAcc.setInfo(info);
            List<Account> list = new AccountList("accounts.csv").getAccountList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == curAcc.getId()) {
                    list.set(i, curAcc);
                    break;
                }
            }
            UpdateDataFromListToFile update = new UpdateDataFromListToFile();
            update.updateAccounts("accounts.csv", list);
        });
        return saveButton;
    }
}
