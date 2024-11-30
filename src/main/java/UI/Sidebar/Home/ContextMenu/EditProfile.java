package UI.Sidebar.Home.ContextMenu;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class EditProfile {
    private final Account curAcc;
    private final TextField fullName;
    private final TextField age;
    private final TextField gender;
    private final TextField phone;
    private final TextField address;
    private final TextField email;

    public EditProfile(Account account) {
        this.curAcc = account;
        UserInfo info = curAcc.getInfo();
        this.fullName = initTextField(info.getFullName());
        this.age = initTextField(String.valueOf(info.getAge()));
        this.gender = initTextField(info.getGender() ? "Male" : "Female");
        this.phone = initTextField(info.getPhoneNumber());
        this.address = initTextField(info.getAddress());
        this.email = initTextField(info.getEmail());
    }

    public StackPane display() {
        Pane text1 = createTextPane(fullName, "Full name:");
        Pane text2 = createTextPane(age, "Age:");
        Pane text3 = createTextPane(gender, "Gender:");
        Pane text4 = createTextPane(phone,  "Phone:");
        Pane text5 = createTextPane(address, "Address:");
        Pane text6 = createTextPane(email, "Email:");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(text1, text2, text3, text4, text5, text6);
        vbox.setTranslateX(300);

        return new StackPane(vbox);
    }

    public TextField initTextField(String text) {
        TextField textField = new TextField();
        textField.setText(text);
        textField.setPrefSize(300, 40);
        textField.setStyle("-fx-font-size: 20px");
        return textField;
    }

    public Pane createTextPane(TextField textField, String type) {
        Label label = new Label(type);
        label.setMinWidth(100);
        label.setStyle("-fx-font-size: 20px");
        HBox hbox = new HBox(10, label, textField);
        hbox.setAlignment(Pos.CENTER_LEFT);
        return new Pane(hbox);
    }

    public Button saveButton() {
        Button saveButton = new Button("Save");
        saveButton.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/home.css")).toExternalForm()
        );
        saveButton.getStyleClass().add("save-button");
        saveButton.setOnMouseClicked(event -> {
            UserInfo info = curAcc.getInfo();
            info.setFullName(fullName.getText());
            info.setAge(Integer.parseInt(age.getText()));
            info.setPhoneNumber(phone.getText());
            info.setAddress(address.getText());
            info.setEmail(email.getText());
            curAcc.setInfo(info);
            ChangePassword.updateAccount(curAcc);
        });
        return saveButton;
    }
}
