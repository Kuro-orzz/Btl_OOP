package Code;

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
}
