package CsvFile;

import Logic.UserInfo;
import UI.Sidebar.BorrowBook.Borrow;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import com.opencsv.CSVWriter;

import java.io.IOException;

public class AppendDataToFile extends InitCsvFile {
    public AppendDataToFile() {}

    /**
     * Append book to the end of file.
     * @param fileName file that book will be appended
     * @param newBook book append to file
     */
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

    /**
     * Append account to the end of file.
     * @param fileName file that account will be appended
     * @param account account append to file
     */
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

    /**
     * Append borrow to the end of file.
     * @param fileName file that borrow will be appended
     * @param borrow borrow append to file
     */
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
