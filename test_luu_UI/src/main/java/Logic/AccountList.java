package Logic;

import java.util.ArrayList;
import java.util.List;

public class AccountList {
    List<Account> Accounts = new ArrayList<Account>();
    public AccountList() {}

    public void addAccount(Account a) {
        Accounts.add(a);
    }

    public void removeAccounts(Account account) {
        for (Account a : Accounts) {
            if (account.getId() == a.getId()) {
                Accounts.remove(a);
                return;
            }
        }
    }

    public boolean searchAccounts(Account account) {
        for (Account a : Accounts) {
            if (account.equals(a)) {
                return true;
            }
        }
        return false;
    }

    public Account findAccount(Account account) {
        for (Account a : Accounts) {
            if (account.equals(a)) {
                return a;
            }
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        for (Account account : Accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null; // Return null if no account with the given username is found
    }
}
