package CsvFile;

import Logic.UserInfo;
import UI.Sidebar.BorrowBook.Borrow;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import com.opencsv.CSVWriter;

import java.io.IOException;

public class AppendDataToFile extends InitCsvFile {
    public AppendDataToFile() {}

    public void appendBook(String fileName, Book newBook) {
        try {
            CSVWriter writer = initCsvWriter(fileName, true);
            String[] info = {
                    newBook.getIsbn(),
                    newBook.getTitle(),
                    newBook.getAuthor(),
                    newBook.getYearOfPublication(),
                    newBook.getPublisher(),
                    newBook.getQuantity()
            };
            writer.writeNext(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendAccount(String fileName, Account account) {
        try {
            CSVWriter writer = initCsvWriter(fileName, true);
            UserInfo userInfo = account.getInfo();
            String[] info = {
                    String.valueOf(account.getId()),
                    account.getUsername(),
                    account.getPassword(),
                    Boolean.toString(account.isAdmin()),
                    userInfo.getFullName(),
                    Integer.toString(userInfo.getAge()),
                    Boolean.toString(userInfo.getGender())
            };
            writer.writeNext(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendBorrow(String fileName, Borrow borrow) {
        try {
            CSVWriter writer = initCsvWriter(fileName, true);
            String[] row = {
                    borrow.getUsername(),
                    borrow.getBookTitle(),
                    borrow.getStatus(),
                    borrow.getBorrowDate()
            };
            writer.writeNext(row);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
