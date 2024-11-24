package UI.Sidebar.UserManagement;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import UI.Sidebar.UserManagement.AccountData.Account;
import Controller.AppController;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class userManagement {
    private TableView<Account> tableView;
    private ObservableList<Account> data;
    private FilteredList<Account> filteredData;

    /**
     * Constructor to create userManagement.
     *
     * @param controller the main controller
     */
    public userManagement(AppController controller) {
        data = FXCollections.observableArrayList();
        filteredData = new FilteredList<>(data, p -> true);
        tableView = new TableView<>(filteredData);
        tableView.getStylesheets().add(getClass()
                .getResource("/styles/userManagement.css").toExternalForm());
        tableView.getStyleClass().add("table-view");
        tableView.setLayoutX(75);
        tableView.setLayoutY(100);

        initializeTableColumns();
        loadAccountData();
    }

    /**
     * Init table column with Cell Value.
     *
     */
    private void initializeTableColumns() {
        TableColumn<Account, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.getStyleClass().add("username-column");
        usernameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsername()));

        TableColumn<Account, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.getStyleClass().add("password-column");
        passwordColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPassword()));

        TableColumn<Account, String> fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.getStyleClass().add("fullname-column");
        fullNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInfo().getFullName()));

        TableColumn<Account, String> ageColumn = new TableColumn<>("Age");
        ageColumn.getStyleClass().add("age-column");
        ageColumn.setCellValueFactory(cellData -> new
                SimpleStringProperty(String.valueOf(cellData.getValue().getInfo().getAge())));

        TableColumn<Account, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.getStyleClass().add("gender-column");
        genderColumn.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().getInfo()
                .getGender() ? "Male" : "Female"));

        TableColumn<Account, String> adminColumn = new TableColumn<>("Admin Status");
        adminColumn.getStyleClass().add("admin-column");
        adminColumn.setCellValueFactory(cellData -> new
                SimpleStringProperty(cellData.getValue().isAdmin() ? "Yes" : "No"));

        tableView.getColumns().addAll(usernameColumn, passwordColumn,
                fullNameColumn, ageColumn, genderColumn, adminColumn);
    }

    /**
     * Read file and load accounts data to ObservableList.
     *
     */
    private void loadAccountData() {
        GetDataFromFile csvReader = new GetDataFromFile();
        List<Account> accounts = csvReader.getAccountsFromFile("accounts.csv");
        Account newSetting = new Account();
        newSetting.setCounter(accounts.get(accounts.size() - 1).getId() + 1);
        if (accounts != null) {
            data.addAll(accounts);
        }
    }

    /**
     * Get User Management GUI Stackpane.
     * @return StackPane
     */
    public StackPane getUserStackPane() {
        // Top AnchorPane for search
        AnchorPane topPane = new AnchorPane();
        topPane.getStyleClass().add("top-pane");

        // Create search mode ComboBox and search Label
        ComboBox<String> searchModeComboBox = new ComboBox<>();
        searchModeComboBox.getItems().addAll("Username", "Full Name", "Age",
                "Gender", "Admin Status");
        searchModeComboBox.setValue("Username");
        searchModeComboBox.getStylesheets().add(getClass()
                .getResource("/styles/userManagement.css").toExternalForm());
        searchModeComboBox.getStyleClass().add("button");
        searchModeComboBox.setLayoutX(160.0);
        searchModeComboBox.setLayoutY(60.0);

        Label searchLabel = new Label("SEARCH BY:");
        searchLabel.setLayoutX(180.0);
        searchLabel.setLayoutY(32.0);

        TextField searchField = new TextField();
        searchField.getStylesheets().add(getClass()
                .getResource("/styles/userManagement.css").toExternalForm());
        searchField.getStyleClass().add("search-field");
        searchField.setLayoutX(272.0);
        searchField.setLayoutY(34.0);
        searchField.setPrefSize(500, 50);
        searchField.setStyle("-fx-background-color: #ffffff;");
        searchField.setPadding(new Insets(10, 10, 10, 10));

        Button addButton = new Button("Add Account");
        addButton.getStylesheets().add(getClass()
                .getResource("/styles/userManagement.css").toExternalForm());
        addButton.getStyleClass().add("button");
        addButton.setLayoutX(800.0);
        addButton.setLayoutY(34.0);
        addButton.setOnAction(e -> openAddAccountStage());

        topPane.getChildren().addAll(searchModeComboBox, searchField, searchLabel, addButton);

        // Set up filtering
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchMode = searchModeComboBox.getValue();
            filteredData.setPredicate(account -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                switch (searchMode) {
                    case "Full Name":
                        return account.getInfo().getFullName().toLowerCase()
                                .contains(lowerCaseFilter);
                    case "Age":
                        return String.valueOf(account.getInfo().getAge())
                                .contains(lowerCaseFilter);
                    case "Gender":
                        return (account.getInfo().getGender() ? "male" : "female")
                                .contains(lowerCaseFilter);
                    case "Admin Status":
                        return (account.isAdmin() ? "yes" : "no")
                                .contains(lowerCaseFilter);
                    default:
                        return account.getUsername().toLowerCase()
                                .contains(lowerCaseFilter);
                }
            });
        });

        AnchorPane centerPane = new AnchorPane(tableView);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setTop(topPane);

        StackPane pane = new StackPane(borderPane);
        StackPane.setAlignment(borderPane, Pos.CENTER);

        return pane;
    }

    /**
     * Open Pop-up stage to add account.
     *
     */
    private void openAddAccountStage() {
        Stage stage = new Stage();
        stage.setTitle("Add New Account");

        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField genderField = new TextField();
        genderField.setPromptText("Gender (true for male, false for female)");

        TextField isAdminField = new TextField();
        isAdminField.setPromptText("Is Admin (true/false)");

        Button doneButton = new Button("Done");
        doneButton.getStylesheets().add(getClass()
                .getResource("/styles/userManagement.css").toExternalForm());
        doneButton.getStyleClass().add("button");
        doneButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String fullName = fullNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            boolean gender = Boolean.parseBoolean(genderField.getText());
            boolean isAdmin = Boolean.parseBoolean(isAdminField.getText());

            UserInfo userInfo = new UserInfo(fullName, age, gender);
            Account newAccount = new Account(username, password, isAdmin, userInfo);

            appendAccountToCSV(newAccount);
            data.add(newAccount);
            stage.close();
        });

        vbox.getChildren().addAll(usernameField, passwordField,
                fullNameField, ageField, genderField, isAdminField, doneButton);

        Scene scene = new Scene(vbox, 300, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Append new account to file.
     * @param account new account to be appended
     */
    private void appendAccountToCSV(Account account) {
            AppendDataToFile csvReader = new AppendDataToFile();
            csvReader.appendAccount("accounts.csv", account);
    }
}