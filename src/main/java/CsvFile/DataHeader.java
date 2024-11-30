package CsvFile;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.UserInfo;

public class DataHeader {
    public final String[] BookInfo = {
            "Isbn",
            "Title",
            "Author",
            "Year Of Publication",
            "Publisher",
            "Quantity"
    };
    public final String[] AccountInfo = {
            "Id",
            "Username",
            "Password",
            "Is Admin",
            "Full Name",
            "Age",
            "Gender",
            "Phone",
            "Email",
            "Address",
            "Number Of Borrowed",
            "Number Of Returned"
    };
    public final String[] BorrowRequestInfo = {
            "Id",
            "Full Name",
            "Isbn",
            "Request date",
            "Status"
    };
    public final String[] BorrowedInfo = {
            "Id",
            "Full name",
            "Isbn",
            "Borrowed date",
            "Due date",
            "Status"
    };

    public String[] getAccountInfo(Account account) {
        UserInfo userInfo = account.getInfo();
        return new String[]{
                String.valueOf(account.getId()),
                account.getUsername(),
                account.getPassword(),
                Boolean.toString(account.isAdmin()),
                userInfo.getFullName(),
                Integer.toString(userInfo.getAge()),
                Boolean.toString(userInfo.getGender()),
                userInfo.getPhoneNumber(),
                userInfo.getEmail(),
                userInfo.getAddress(),
                userInfo.getNumberOfBorrowed(),
                userInfo.getNumberOfReturned()
        };
    }
}
