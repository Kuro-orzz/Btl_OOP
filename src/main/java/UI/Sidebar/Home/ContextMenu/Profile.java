package UI.Sidebar.Home.ContextMenu;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Profile {
    private final Label idLabel;
    private final Label usernameLabel;
    private final Label fullNameLabel;
    private final Label ageLabel;
    private final Label genderLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label addressLabel;

    public Profile(Account account) {
        UserInfo info = account.getInfo();
        this.idLabel = initLabel("Id: " + account.getId());
        this.usernameLabel = initLabel("Username: " + account.getUsername());
        this.fullNameLabel = initLabel("Full Name: " + info.getFullName());
        this.ageLabel = initLabel("Age: " + info.getAge());
        this.genderLabel = initLabel("Gender: " + info.getGender());
        this.phoneLabel = initLabel("Phone: " + info.getPhoneNumber());
        this.emailLabel = initLabel("Email: " + info.getEmail());
        this.addressLabel = initLabel("Address: " + info.getAddress());
    }

    public StackPane display() {
        VBox detailBox = new VBox(10, idLabel, usernameLabel, fullNameLabel, ageLabel,
                genderLabel, phoneLabel, emailLabel, addressLabel);
        detailBox.setPadding(new Insets(10, 10, 10, 70));
        detailBox.setStyle("-fx-font-size: 20px");

        StackPane stackPane = new StackPane(detailBox);
        stackPane.setAlignment(Pos.TOP_CENTER);
        return stackPane;
    }

    private Label initLabel(String text) {
        return new Label(text);
    }
}

