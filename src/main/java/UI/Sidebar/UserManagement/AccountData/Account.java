package UI.Sidebar.UserManagement.AccountData;

import Optimize.HashingMultipleBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Account extends HashingMultipleBase {
    private static int counter = 1;
    private int id;
    private String username;
    private String password;
    private boolean isAdmin = false;
    public HashingMultipleBase hashId, hashUsername, hashPassword,
            hashIsAdmin, hashFullName, hashAge, hashGender,
            hashPhone, hashEmail, hashAddress;

    private UserInfo info;

    public Account() {}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = counter++;
    }

    public Account(String username, String password, boolean isAdmin, UserInfo info) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.id = counter++;
        this.info = info;
    }

    public Account(String username, String password, int t) {
        this.username = username;
        this.password = password;
    }

    public Account(String[] data) {
        this.id = Integer.parseInt(data[0]);
        this.username = data[1];
        this.password = data[2];
        this.isAdmin = Boolean.parseBoolean(data[3]);
        info = new UserInfo(data[4], Integer.parseInt(data[5]), Boolean.parseBoolean(data[6]));
        info.setPhoneNumber(data[7]);
        info.setEmail(data[8]);
        info.setAddress(data[9]);
        info.setNumberOfBorrowed(data[10]);
        info.setNumberOfReturned(data[11]);
    }

    public static void setCounter(int counter) {
        Account.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public ObservableValue<String> idProperty() {
        return new SimpleStringProperty(String.valueOf(id));
    }

    public ObservableValue<String> usernameProperty() {
        return new SimpleStringProperty(username);
    }

    public ObservableValue<String> passwordProperty() {
        return new SimpleStringProperty(password);
    }

    public ObservableValue<String> isAdminProperty() {
        return new SimpleStringProperty(isAdmin ? "Admin" : "User");
    }

    public boolean equals(Object obj) {
        if (obj instanceof Account account) {
            return account.username.equals(this.username)
                    && account.password.equals(this.password);
        }
        return false;
    }
}