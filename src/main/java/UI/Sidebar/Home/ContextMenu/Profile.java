package UI.Sidebar.Home.ContextMenu;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Profile {

    public Profile() {}

    public StackPane display(Account curAcc) {
        UserInfo info = curAcc.getInfo();
        Label idLabel = initLabel("Id: " + curAcc.getId());
        Label usernameLabel = initLabel("Username: " + curAcc.getUsername());
        Label fullnameLabel = initLabel("Full Name: " + info.getFullName());
        Label ageLabel = initLabel("Age: " + info.getAge());
        Label genderLabel = initLabel("Gender: " + info.getGender());
        Label phoneLabel = initLabel("Phone: " + (info.getPhoneNumber() == null ? "" : info.getPhoneNumber()));
        Label emailLabel = initLabel("Email: " + (info.getEmail() == null ? "" : info.getEmail()));
        Label addressLabel = initLabel("Address: " + (info.getAddress() == null ? "" : info.getAddress()));

        VBox detailBox = new VBox(10, idLabel, usernameLabel, fullnameLabel, ageLabel,
                genderLabel, phoneLabel, emailLabel, addressLabel);
        detailBox.setPadding(new Insets(10, 10, 10, 70));
        detailBox.setStyle("-fx-font-size: 20px");

        StackPane stackPane = new StackPane(detailBox);
        stackPane.setAlignment(Pos.TOP_CENTER);
        return stackPane;
    }

    private Label initLabel(String text) {
        Label label = new Label(text);
        return label;
    }
}

