package UI.Sidebar.UserManagement.AccountData;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;

import java.util.ArrayList;
import java.util.List;

public class AccountList {
    private List<Account> accountList = new ArrayList<Account>();

    public AccountList() {}

    public AccountList(String fileName) {
        accountList = new GetDataFromFile().getAccountsFromFile(fileName);
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
        AppendDataToFile append = new AppendDataToFile();
        append.appendAccount("accounts.csv", account);
    }

    public void removeAccounts(Account account) {
        accountList.remove(account);
        UpdateDataFromListToFile update = new UpdateDataFromListToFile();
        update.updateAccounts("accounts.csv", accountList);
    }

    public boolean isUserNameExists(String userName) {
        for (Account account : accountList) {
            if (account.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
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

    public int getTotalUsers() {
        return accountList.size();
    }
}