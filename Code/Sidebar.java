package Code;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sidebar extends Application {

    public void createIcon() {

    }

    public Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(200);

        // padding: top right bottom left
        button.setStyle("-fx-font-size: 20px; -fx-background-color: #243A73; -fx-text-fill: white; -fx-alignment: center-left;");

        // Event when mouse in
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 20px; -fx-background-color: #3A4E8D; -fx-text-fill: white; -fx-alignment: center-left;"));

        // Event when mouse out
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 20px; -fx-background-color: #243A73; -fx-text-fill: white; -fx-alignment: center-left;"));

        return button;
    }


    @Override
    public void start(Stage stage) {
        // Sidebar menu items
        VBox sidebar = new VBox();
        sidebar.setPadding(new Insets(30));
        sidebar.setSpacing(5);
        sidebar.setStyle("-fx-background-color: #1D2D50;");

        Label title = new Label("Library management");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 0 0 50px 0;");

        Button overviewButton = createSidebarButton("Dashboard");
        Button bookButton = createSidebarButton("Library");
        Button magazineButton = createSidebarButton("List borrow");
        Button loanButton = createSidebarButton("Processing");
        Button readerButton = createSidebarButton("Borrow");
        Button systemButton = createSidebarButton("Return");
        Button reportButton = createSidebarButton("Search");
        Button trainingButton = createSidebarButton("Statistic");

        sidebar.getChildren().addAll(title, overviewButton, bookButton, magazineButton, loanButton,
                readerButton, systemButton, reportButton, trainingButton);

        // BorderPane layout
        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);

        Scene scene = new Scene(layout, 1280, 720);
        stage.setTitle("Sidebar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
