package UI.Sidebar.UserManagement;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;
import UI.Sidebar.UserManagement.AccountData.Account;
import Controller.AppController;
import UI.Sidebar.UserManagement.AccountData.AccountList;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

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
        tableView.setLayoutX(100);
        tableView.setLayoutY(20);

        initializeTableColumns();
        loadAccountData();

        addTableContextMenu();
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
        List<Account> accounts = new GetDataFromFile().getAccountsFromFile("accounts.csv");
        data.addAll(accounts);
    }

    /**
     * Get User Management GUI Stackpane.
     * @param currentAccount current Account
     * @return StackPane
     */
    public StackPane getUserStackPane(Account currentAccount) {
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

        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleButton = new RadioButton("Male");
        maleButton.setToggleGroup(genderGroup);
        maleButton.setUserData(true);

        RadioButton femaleButton = new RadioButton("Female");
        femaleButton.setToggleGroup(genderGroup);
        femaleButton.setUserData(false);

        HBox genderBox = new HBox(10, maleButton, femaleButton);

        ToggleGroup adminGroup = new ToggleGroup();
        RadioButton adminYesButton = new RadioButton("Yes");
        adminYesButton.setToggleGroup(adminGroup);
        adminYesButton.setUserData(true);

        RadioButton adminNoButton = new RadioButton("No");
        adminNoButton.setToggleGroup(adminGroup);
        adminNoButton.setUserData(false);

        HBox adminBox = new HBox(10, adminYesButton, adminNoButton);

        Button doneButton = new Button("Done");
        doneButton.getStylesheets().add(getClass().getResource("/styles/userManagement.css").toExternalForm());
        doneButton.getStyleClass().add("button");
        doneButton.setOnAction(e -> {
            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String fullName = fullNameField.getText();
                String ageText = ageField.getText();

                if (!username.matches("^[a-zA-Z0-9]{6,20}$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Username", "Username must be 6-20 alphanumeric characters without special characters.");
                    return;
                }

                if (!password.matches("^[a-zA-Z0-9!@#$%^&*()_+]{6,20}$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Password", "Password must be 6-20 characters and can include special characters.");
                    return;
                }

                if (!fullName.matches("^[A-Z][a-z]+( [A-Z][a-z]+)+$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Full Name", "Full name must contain at least two words, each starting with an uppercase letter.");
                    return;
                }

                if (fullName.length() < 6 || fullName.length() > 20) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Full Name", "Full name must be between 6 and 20 characters long.");
                    return;
                }

                int age;
                try {
                    age = Integer.parseInt(ageText);
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Age", "Age must be a valid number.");
                    return;
                }

                if (age <= 0 || age >= 100) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Age", "Age must be between 1 and 99.");
                    return;
                }

                Toggle selectedGender = genderGroup.getSelectedToggle();
                if (selectedGender == null) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Gender", "Please select Male or Female.");
                    return;
                }
                boolean gender = (boolean) selectedGender.getUserData();

                Toggle selectedAdmin = adminGroup.getSelectedToggle();
                if (selectedAdmin == null) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Admin Status", "Please select Yes or No for Admin.");
                    return;
                }
                boolean isAdmin = (boolean) selectedAdmin.getUserData();

                UserInfo userInfo = new UserInfo(fullName, age, gender);
                Account newAccount = new Account(username, password, isAdmin, userInfo);

                appendAccountToCSV(newAccount);
                data.add(newAccount);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Account added successfully!");
                stage.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(usernameField, passwordField, fullNameField, ageField,
                new Label("Gender:"), genderBox, new Label("Admin Status:"),
                adminBox, doneButton);

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

    /**
     * Method to add context menu to table rows.
     */
    private void addTableContextMenu() {
        tableView.setRowFactory(tv -> {
            TableRow<Account> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem viewDetails = new MenuItem("View Details");
            viewDetails.setOnAction(event -> {
                Account selectedAcc = row.getItem();
                if (selectedAcc != null) {
                    showAccountDetails(selectedAcc);
                }
            });

            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(event -> {
                Account selectedAcc = row.getItem();
                if (selectedAcc != null) {
                    openEditAccountStage(selectedAcc);
                }
            });

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                Account selectedAcc = row.getItem();
                if (selectedAcc != null) {
                    deleteAccount(selectedAcc);
                }
            });

            contextMenu.getItems().addAll(viewDetails, editItem, deleteItem);

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }

    private void openEditAccountStage(Account account) {
        Stage stage = new Stage();
        stage.setTitle("Edit Account");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        TextField usernameField = new TextField(account.getUsername());
        usernameField.setPromptText("Username");
        TextField passwordField = new TextField(account.getPassword());
        passwordField.setPromptText("Password");

        TextField fullnameField = new TextField(account.getInfo().getFullName());
        fullnameField.setPromptText("Full Name");

        TextField ageField = new TextField(String.valueOf(account.getInfo().getAge()));
        ageField.setPromptText("Age");

        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleButton = new RadioButton("Male");
        maleButton.setToggleGroup(genderGroup);
        maleButton.setUserData(true);

        RadioButton femaleButton = new RadioButton("Female");
        femaleButton.setToggleGroup(genderGroup);
        femaleButton.setUserData(false);

        if (account.getInfo().getGender()) {
            maleButton.setSelected(true);
        } else {
            femaleButton.setSelected(true);
        }

        HBox genderBox = new HBox(10, maleButton, femaleButton);

        ToggleGroup adminGroup = new ToggleGroup();
        RadioButton adminYesButton = new RadioButton("Yes");
        adminYesButton.setToggleGroup(adminGroup);
        adminYesButton.setUserData(true);

        RadioButton adminNoButton = new RadioButton("No");
        adminNoButton.setToggleGroup(adminGroup);
        adminNoButton.setUserData(false);

        if (account.isAdmin()) {
            adminYesButton.setSelected(true);
        } else {
            adminNoButton.setSelected(true);
        }

        HBox adminBox = new HBox(10, adminYesButton, adminNoButton);

        Button doneButton = new Button("Done");
        doneButton.getStylesheets().add(getClass().getResource("/styles/userManagement.css").toExternalForm());
        doneButton.getStyleClass().add("button");
        doneButton.setOnAction(e -> {
            try {
                String newUsername = usernameField.getText();
                String newPassword = passwordField.getText();
                String newFullName = fullnameField.getText();
                String newAgeText = ageField.getText();

                if (!newUsername.matches("^[a-zA-Z0-9]{6,20}$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Username", "Username must be 6-20 alphanumeric characters without special characters.");
                    return;
                }

                if (!newPassword.matches("^[a-zA-Z0-9!@#$%^&*()_+]{6,20}$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Password", "Password must be 6-20 characters and can include special characters.");
                    return;
                }

                if (!newFullName.matches("^[A-Z][a-z]+( [A-Z][a-z]+)+$")) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Full Name", "Full name must contain at least two words, each starting with an uppercase letter.");
                    return;
                }

                if (newFullName.length() < 6 || newFullName.length() > 20) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Full Name", "Full name must be between 6 and 20 characters long.");
                    return;
                }

                int newAge;
                try {
                    newAge = Integer.parseInt(newAgeText);
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Age", "Age must be a number.");
                    return;
                }
                if (newAge <= 0 || newAge >= 100) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Age", "Age must be between 1 and 99.");
                    return;
                }

                Toggle selectedGender = genderGroup.getSelectedToggle();
                if (selectedGender == null) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Gender", "Please select Male or Female.");
                    return;
                }
                boolean newGender = (boolean) selectedGender.getUserData();

                Toggle selectedAdmin = adminGroup.getSelectedToggle();
                if (selectedAdmin == null) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Admin Status", "Please select Yes or No for Admin.");
                    return;
                }
                boolean newIsAdmin = (boolean) selectedAdmin.getUserData();

                account.setUsername(newUsername);
                account.setPassword(newPassword);
                account.setAdmin(newIsAdmin);
                account.setInfo(new UserInfo(newFullName, newAge, newGender));

                updateAccountInCsv(account);
                tableView.refresh();
                stage.close();
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(usernameField, passwordField, fullnameField, ageField,
                new Label("Gender:"), genderBox, new Label("Is Admin:"), adminBox, doneButton);

        Scene scene = new Scene(vbox, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void updateAccountInCsv(Account account) {
        // Implement method to update the account information in the CSV file
        UpdateDataFromListToFile csvWriter = new UpdateDataFromListToFile();
        List<Account> accounts = new AccountList("accounts.csv").getAccountList();
        accounts.removeIf(b -> b.getUsername().equals(account.getUsername())); // Remove old book entry
        accounts.add(account); // Add updated book entry
        csvWriter.updateAccounts("accounts.csv", accounts);
    }

    private void deleteAccount(Account account) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this account?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            data.remove(account);
            removeAccountFromCSV(account);
        }
    }

    private void removeAccountFromCSV(Account account) {
        UpdateDataFromListToFile csvWriter = new UpdateDataFromListToFile();
        List<Account> accounts = new AccountList("accounts.csv").getAccountList();
        accounts.removeIf(b -> b.getUsername().equals(account.getUsername())); // Remove the book
        csvWriter.updateAccounts( "accounts.csv", accounts);
    }

    /**
     * Method to show book details in a new stage.
     * @param account the selected book to display details for.
     */
    private void showAccountDetails(Account account) {
        Stage stage = new Stage();
        stage.setTitle("Book Details");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
//
//        Label isbnLabel = new Label("ISBN: " + account.getIsbn());
//        Label titleLabel = new Label("Title: " + book.getTitle());
//        Label authorLabel = new Label("Author: " + book.getAuthor());
//        Label yearLabel = new Label("Year of Publication: " + book.getYearOfPublication());
//        Label publisherLabel = new Label("Publisher: " + book.getPublisher());
//        Label quantityLabel = new Label("Quantity: " + book.getQuantity());
//
//        vbox.getChildren().addAll(isbnLabel, titleLabel, authorLabel, yearLabel, publisherLabel, quantityLabel);
        Label usernameLabel = new Label("Username: " + account.getUsername());
        Label passwordLabel = new Label("Password: " + account.getPassword());
        Label isAdminLabel = new Label("Role: " + (account.isAdmin() ? "Admin" : "User"));
        Label fullnameLabel = new Label("Full Name: " + account.getInfo().getFullName());
        Label ageLabel = new Label("Age: " + account.getInfo().getAge());
        Label genderLabel = new Label("Gender: " + (account.getInfo().getGender() ? "Male" : "Female"));

        vbox.getChildren().addAll(usernameLabel, passwordLabel, isAdminLabel, fullnameLabel, ageLabel, genderLabel);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}