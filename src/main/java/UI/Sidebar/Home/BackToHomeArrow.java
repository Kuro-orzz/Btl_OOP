package UI.Sidebar.Home;

import UI.Sidebar.Home.ContextMenu.Profile;
import UI.Sidebar.UserManagement.AccountData.Account;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.Objects;

public class BackToHomeArrow {
    private final Account curAcc;
    private final BorderPane layout;
    private final Button backToHomeButton;

    public BackToHomeArrow(Account curAcc, BorderPane layout) {
        this.curAcc = curAcc;
        this.layout = layout;
        this.backToHomeButton = new Button("Back to Home");
    }

    public StackPane display() {
        backToHomeButton.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/home.css")).toExternalForm()
        );
        backToHomeButton.getStyleClass().add("return-button");
        addButtonEvent();
        backToHomeButton.setAlignment(Pos.CENTER);

        return new StackPane(backToHomeButton);
    }

    public void addButtonEvent() {
        backToHomeButton.setOnMouseClicked(e -> {
            System.out.println("Arrow button clicked!");
            Profile profile = new Profile(curAcc);
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), profile.display());
            transition.setToX(-600);  // Move the profile view off-screen
            transition.setOnFinished(event -> {
                // Once the animation is finished, switch to the home scene
                StackPane homeStPane = new Home(layout).getHomeStackPane(curAcc);
                layout.setCenter(homeStPane);
                // Reset the profilePane position to its original place for next transition
                profile.display().setTranslateX(0);
            });
            transition.play();
        });
    }
}
