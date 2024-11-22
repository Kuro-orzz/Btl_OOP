package UI.Sidebar.UserManagement.AccountData;

import Logic.CsvReader;

import java.util.ArrayList;
import java.util.List;

public class AccountList extends CsvReader {
    private List<Account> accountList = new ArrayList<Account>();

    public AccountList() {}

    public AccountList(String fileName) {
        accountList = new CsvReader().getAccountsFromFile(fileName);
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void addAccount(Account a) {
        accountList.add(a);
    }

    public void removeAccounts(Account account) {
        for (Account a : accountList) {
            if (account.getId() == a.getId()) {
                accountList.remove(a);
                return;
            }
        }
    }

    public boolean isAccountsExist(Account account) {
        for (Account a : accountList) {
            if (account.getUsername().equals(a.getUsername()) &&
                    account.getPassword().equals(a.getPassword())) {
                return true;
            }
        }
        return false;
    }


    public Account findAccount(Account account) {
        for (Account a : accountList) {
            if (account.equals(a)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Get account this a specific username.
     * @param username username of account
     * @return account that has that username
     */
    public Account getAccountByUsername(String username) {
        for (Account account : accountList) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null; // Return null if no account with the given username is found
    }
}