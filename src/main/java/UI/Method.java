package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Objects;

public class Method<T> {
    public void applyStylesAndClasses(Control control, String stylesheetPath, String styleClass) {
        URL path = getClass().getResource(stylesheetPath);
        control.getStylesheets().add(
                Objects.requireNonNull(path).toExternalForm()
        );
        control.getStyleClass().add(styleClass);
    }

    public void setTableView(TableView<T> tableView) {
        tableView.setPrefSize(840, 576);
        tableView.setStyle("-fx-border-style: solid; -fx-border-color: #CCCCCC;");
        tableView.setLayoutX(100);
        tableView.setLayoutY(20);
    }

    public ComboBox<String> initSearchBox(String[] searchType, String styleSheetPath, String styleClass) {
        ComboBox<String> searchBox = new ComboBox<>();
        searchBox.getItems().addAll(searchType);
        searchBox.setValue("Title");
        searchBox.setLayoutX(160.0);
        searchBox.setLayoutY(60.0);
        applyStylesAndClasses(searchBox, styleSheetPath, styleClass);
        return searchBox;
    }

    public Label initsearchLabel() {
        Label searchLabel = new Label("SEARCH BY:");
        searchLabel.setLayoutX(180.0);
        searchLabel.setLayoutY(32.0);
        return searchLabel;
    }

    public TextField initSearchField(String styleSheetPath, String styleClass) {
        TextField searchField = new TextField();
        applyStylesAndClasses(searchField, styleSheetPath, styleClass);
        searchField.setLayoutX(272.0);
        searchField.setLayoutY(34.0);
        searchField.setPrefSize(500, 50);
        searchField.setStyle("-fx-background-color: #ffffff;");
        searchField.setPadding(new Insets(10, 10, 10, 10));
        return searchField;
    }

    public Button initButton(String text, String styleSheetPath, String styleClass) {
        Button button = new Button(text);
        applyStylesAndClasses(button, styleSheetPath, styleClass);
        button.setLayoutX(800.0);
        button.setLayoutY(34.0);
        return button;
    }

    public StackPane initStackPane(AnchorPane topPane, TableView<T> tableView) {
        AnchorPane centerPane = new AnchorPane(tableView);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setTop(topPane);

        StackPane stackPane = new StackPane(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);

        return stackPane;
    }
}
