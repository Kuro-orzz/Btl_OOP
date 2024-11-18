package Logic;

public class Account {
    private static int counter = 1;
    private int id;
    private String username;
    private String password;
    private boolean isAdmin = false;

    private UserInfo info;

    public Account() {
        this.id = counter++;
    }

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

    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            return account.username.equals(this.username) && account.password.equals(this.password);
        }
        return false;
    }
}
