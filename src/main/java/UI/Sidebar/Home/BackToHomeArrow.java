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

    public BackToHomeArrow(Account curAcc, BorderPane layout) {
        this.curAcc = curAcc;
        this.layout = layout;
    }

    public StackPane display() {
        Button arrowButton = new Button("Back to home");
        arrowButton.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/styles/home.css")).toExternalForm()
        );
        arrowButton.getStyleClass().add("return-button");
        // Set an action for the button when clicked
        arrowButton.setOnMouseClicked(e -> {
            System.out.println("Arrow button clicked!");
            Profile profile = new Profile();
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), profile.display(curAcc));
            transition.setToX(-600);  // Move the profile view off-screen
            transition.setOnFinished(event -> {
                // Once the animation is finished, switch to the home scene
                StackPane homeStPane = new Home(layout).getHomeStackPane(curAcc);
                layout.setCenter(homeStPane);
                // Reset the profilePane position to its original place for next transition
                profile.display(curAcc).setTranslateX(0);
            });
            transition.play();
        });
        arrowButton.setAlignment(Pos.CENTER);

        return new StackPane(arrowButton);
    }
}
