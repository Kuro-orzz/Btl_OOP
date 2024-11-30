package CsvFile;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.UserManagement.AccountData.UserInfo;

public class DataHeader {
    public final String[] BookInfo = {
            "ISBN",
            "Book-Title",
            "Book-Author",
            "Year-of-Publication",
            "Publisher",
            "Quantity"
    };
    public final String[] AccountInfo = {
            "Id",
            "Username",
            "Password",
            "IsAdmin",
            "FullName",
            "Age",
            "Gender",
            "Phone",
            "Email",
            "Address",
            "NumberOfBorrowed",
            "NumberOfReturned"
    };
    public final String[] BorrowRequestInfo = {
            "Id",
            "Full name",
            "ISBN",
            "Request date",
            "Status"
    };
    public final String[] BorrowedInfo = {
            "Id",
            "Full name",
            "ISBN",
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
