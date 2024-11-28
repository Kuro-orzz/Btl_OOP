package UI.Sidebar.Home;

import Controller.MainApp;
import UI.Sidebar.Home.ContextMenu.BackToHomeArrow;
import UI.Sidebar.Home.ContextMenu.EditProfile;
import UI.Sidebar.Home.ContextMenu.Profile;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class AvatarImage {
    private final Account curAcc;
    private final BorderPane layout;

    public AvatarImage(Account acc, BorderPane layout) {
        curAcc = acc;
        this.layout = layout;
    }

    public StackPane profileButton() {
        Image profileImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/GUI/profileImage.png"))
        );

        ImageView profile = new ImageView(profileImage);
        profile.setFitWidth(50);
        profile.setPreserveRatio(true);

        // CenterX, CenterY, Radius
        Circle clip = new Circle(25, 25, 25);
        profile.setClip(clip);

        // Add a border effect
        Circle border = new Circle(25, 25, 25);
        border.setStroke(Color.BLACK);
        border.setFill(Color.TRANSPARENT);
        border.setStrokeWidth(2);

        ContextMenu contextMenu = initContextMenu();

        Label username = new Label(curAcc.getUsername());
        username.setStyle("-fx-font-size: 20px");

        StackPane stackPane = new StackPane(profile, border);
        setOnActionForProfile(stackPane, border, contextMenu);

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(username, stackPane);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(30, 30, 0, 0));

        return new StackPane(hBox);
    }

    public void setOnActionForProfile(StackPane profile, Circle border, ContextMenu contextMenu) {
        profile.setOnMouseClicked(event -> {
            Bounds bounds = profile.localToScreen(profile.getBoundsInLocal());
            double posX = bounds.getMinX() + (bounds.getWidth() / 2) - contextMenu.getWidth() / 2;
            double posY = bounds.getMaxY();

            contextMenu.show(profile, posX - 30, posY);
        });

        profile.setOnMouseEntered(event -> {
            border.setOpacity(0.7);
            profile.setOpacity(0.7);
        });

        profile.setOnMouseExited(event -> {
            border.setOpacity(1);
            profile.setOpacity(1);
        });
    }

    public ContextMenu initContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem profileItem = new MenuItem("Profile");
        profileItem.setStyle("-fx-font-size: 20px");
        profileItem.setOnAction(event -> {
            Profile profile = new Profile();
            BackToHomeArrow arrow = new BackToHomeArrow(curAcc, layout);
            VBox vBox = new VBox(30);
            vBox.getChildren().addAll(new AvatarImage(curAcc, layout).profileButton(), profile.displayProfile(curAcc), arrow.displayArrow());
            StackPane stackPane = new StackPane(vBox);
            stackPane.setLayoutX(30);
            layout.setCenter(stackPane);
        });

        MenuItem editProfileItem = new MenuItem("Edit profile");
        editProfileItem.setStyle("-fx-font-size: 20px");
        editProfileItem.setOnAction(event -> {
            EditProfile edit = new EditProfile(curAcc, layout);
            BackToHomeArrow arrow = new BackToHomeArrow(curAcc, layout);
            VBox vBox = new VBox(15);
            vBox.getChildren().addAll(new AvatarImage(curAcc, layout).profileButton(), edit.displayEdit(), arrow.displayArrow());
            StackPane stackPane = new StackPane(vBox, edit.saveButton());
            layout.setCenter(stackPane);
        });

        MenuItem changePasswordItem = new MenuItem("Change password");
        changePasswordItem.setStyle("-fx-font-size: 20px");
        changePasswordItem.setOnAction(event -> {
            System.out.println("Change password selected");
        });

        // Add items to the context menu
        contextMenu.getItems().addAll(profileItem, editProfileItem, changePasswordItem);

        return contextMenu;
    }
}
