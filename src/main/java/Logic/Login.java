package Logic;

import AccountData.Account;
import AccountData.AccountList;

import java.util.Scanner;

public class Login extends AccountList {
    private boolean isLogin = false;

    public void run() {
        showOption();
    }

    public void showOption() {
        System.out.println("Login or Create account (1 or 2)");
        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine();
        try {
            int number = Integer.parseInt(option);
            if (number == 1) {
                loginAccount();
            } else if (number == 2) {
                createAccount();
            } else {
                System.out.println("Invalid option");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid option");
        }
    }

    public void loginAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        if (isAccountsExist(new Account(username, password, 1))) {
            System.out.println("Login successful");
            isLogin = true;
            System.out.println(findAccount(new Account(username, password, 1)).getId());
        } else {
            System.out.println("Invalid username or password");
        }
    }

    public void createAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        addAccount(new Account(username, password));
    }

    public void logOut() {
        isLogin = false;
    }

    public boolean checkLogin() {
        return isLogin;
    }
}
